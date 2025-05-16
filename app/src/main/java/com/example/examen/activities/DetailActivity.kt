package com.example.examen.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examen.Movie
import com.example.examen.R
import com.example.examen.data.MovieService
import com.example.examen.databinding.ActivityDetailBinding
import com.example.examen.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getStringExtra("MOVIE_ID")

        getMoviesById(id!!)

        binding.navigationBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_info -> {
                    binding.plotContent.root.visibility = View.GONE
                    binding.infoContent.root.visibility = View.VISIBLE

                }

                R.id.action_plot -> {
                    binding.infoContent.root.visibility = View.GONE
                    binding.plotContent.root.visibility = View.VISIBLE
                }

            }
            true
        }
        binding.navigationBar.selectedItemId = R.id.action_info
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun loadData() {
        supportActionBar?.title = movie.Title

        Picasso.get().load(movie.Poster).into(binding.posterImageView)

        //INFO
        binding.infoContent.titleDetailTextView.text = movie.Title
        binding.infoContent.directorDetailTextView.text = movie.Director
        binding.infoContent.yearDetailTextView.text = movie.Year
        binding.infoContent.genreDetailTextView.text = movie.Genre
        binding.infoContent.countryDetailTextView.text = movie.Country
        binding.infoContent.runtimeDetailTextView.text = movie.Runtime

        //PLOT
        binding.plotContent.plotDetailTextView.text = movie.Plot
    }

    fun getMoviesById(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = MovieService.getInstance()
                movie = service.findMoviesById(id)

                CoroutineScope(Dispatchers.Main).launch {
                    binding.progress.visibility = View.GONE
                    loadData()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}