package com.example.examen.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.examen.data.Movie
import com.example.examen.R
import com.example.examen.adapters.MovieAdapter
import com.example.examen.data.MovieService
import com.example.examen.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var movieList: List<Movie> = emptyList()

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: MovieAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Movies"

        searchMoviesByName("You")

        /*adapter = MovieAdapter(movieList) {  position ->
            val movie = movieList [position]
            val intent = Intent (this, DetailActivity::class.java)
            intent.putExtra("MOVIE_ID",movie.imdbID)
            startActivity(intent)
        }*/
        adapter = MovieAdapter(emptyList()) { position ->
            navigateToDetail(movieList[position])
        }


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager (this, 2)

    }

    override fun onResume() {
        super.onResume()

        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_activity_main, menu)

        val menuItem = menu?.findItem(R.id.action_search)
        val searchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchMoviesByName(query)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_saved_movies -> {
                val intent = Intent(this, MyMoviesActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }



    fun searchMoviesByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = MovieService.getInstance()
                val result = service.findMoviesByName(query)

                movieList = result.movie.filter { it.Poster != "N/A" }

                CoroutineScope(Dispatchers.Main).launch {
                    adapter.items = movieList
                    adapter.notifyDataSetChanged()
                    binding.recyclerView.scrollToPosition(0)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    fun navigateToDetail(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.imdbID)
        startActivity(intent)
    }
}