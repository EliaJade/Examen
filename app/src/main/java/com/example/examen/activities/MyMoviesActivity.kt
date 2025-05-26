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
import androidx.core.view.setPadding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen.R
import com.example.examen.adapters.MyMoviesAdapter
import com.example.examen.data.Movie
import com.example.examen.data.MyMovies
import com.example.examen.data.MyMoviesDAO
import com.example.examen.data.Status
import com.example.examen.databinding.ActivityMyMoviesBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class MyMoviesActivity: AppCompatActivity() {

    lateinit var binding: ActivityMyMoviesBinding

    var myMovieList: List<MyMovies> = emptyList()

    lateinit var myMoviesDAO: MyMoviesDAO

    lateinit var movie: Movie

    var filterQuery = ""
    var filterCategory: Status? = null

    lateinit var adapter: MyMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMyMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.title = getString(R.string.my_movies)

        myMoviesDAO = MyMoviesDAO(this)

        adapter = MyMoviesAdapter(myMovieList) { position ->
            navigateToDetail(myMovieList[position])
    }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        binding.tabBar.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position) {
                    0 -> filterCategory = null
                    1-> filterCategory = Status.WANT_TO_WATCH
                    2 -> filterCategory = Status.UNFINISHED
                    3 -> filterCategory = Status.WATCHED
                }
                refreshData()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}

        })
    }

    override fun onResume() {
        super.onResume()
        refreshData()

        myMovieList = myMoviesDAO.findAll()
        adapter.items = myMovieList
        adapter.notifyDataSetChanged()
    }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true

            }
            else -> super.onOptionsItemSelected(item)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_my_movies_search, menu)

        val menuItem = menu?.findItem(R.id.action_search_my_movies)
        val searchView = menuItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                filterQuery = query
                refreshData()
                return true
            }
        })

        return true
    }

    fun refreshData() {
        myMovieList = myMoviesDAO.findByMyMovieName(filterQuery,
            filterCategory
        )
        adapter.updateItems(myMovieList)
    }

    fun navigateToDetail(book: MyMovies) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movie.imdbID)
        startActivity(intent)
    }
}
