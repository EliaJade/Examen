package com.example.examen.activities

import android.os.Bundle
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

        val id = intent.getStringExtra("MOVIE_ID")

        getMoviesById(id!!)
    }

    fun loadData() {
        supportActionBar?.title = movie.Title

        Picasso.get().load(movie.Poster).into(binding.posterImageView)
        binding.titleDetailTextView.text = movie.Title
        binding.yearDetailTextView.text = movie.Year
        binding.plotDetailTextView.text = movie.Plot
        binding.runtimeDetailTextView.text = movie.Runtime
        binding.genreDetailTextView.text = movie.Genre
        binding.directorDetailTextView.text = movie.Director
        binding.runtimeDetailTextView.text = movie.Runtime


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