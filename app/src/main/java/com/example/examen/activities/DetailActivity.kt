package com.example.examen.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examen.data.Movie
import com.example.examen.R
import com.example.examen.data.MovieService
import com.example.examen.data.MyMovies
import com.example.examen.data.MyMoviesDAO
import com.example.examen.data.Status
import com.example.examen.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    lateinit var movie: Movie

    var saveMenu: MenuItem? = null
    var myMovies: MyMovies? = null

    lateinit var myMoviesDAO: MyMoviesDAO

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

        myMoviesDAO = MyMoviesDAO(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getStringExtra("MOVIE_ID")!!

        myMovies = myMoviesDAO.findById(id)
        getMoviesById(id)

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


    private fun setSavedIcon() {
        if (myMovies != null) {
            saveMenu?.setIcon(R.drawable.archive_check_green)
        } else {
            saveMenu?.setIcon(R.drawable.archive)
        }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)

        saveMenu = menu.findItem(R.id.action_save)

        setSavedIcon()

        return true
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

        loadStatus()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            android.R.id.home -> {
                finish()
                true
            }

            R.id.action_save -> {
                if (myMovies != null) {
                    myMoviesDAO.delete(myMovies!!)
                    myMovies = null
                    loadStatus()
                } else {
                    showStatusAlertDialog()
                }
                setSavedIcon()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }


    private fun loadStatus() {
        if (myMovies != null) {
            val iconId = when(myMovies!!.Status!!) {
                Status.WATCHED -> {
                    Status.WATCHED.icon
                }
                Status.UNFINISHED -> {
                    Status.UNFINISHED.icon
                }
                Status.WANT_TO_WATCH -> {
                    Status.WANT_TO_WATCH.icon
                }
            }

            binding.statusChip.setChipIconResource(iconId)
            binding.statusChip.text = getString(myMovies!!.Status!!.title)
            binding.statusChip.visibility = View.VISIBLE

        } else {
            binding.statusChip.visibility = View.GONE
        }
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
    fun showStatusAlertDialog() {
        val statusList = Status.entries.map { getString(it.title) }.toTypedArray()
        var status: Status = Status.WANT_TO_WATCH
        val checkedIndex = myMovies?.Status?.ordinal ?: -1

        val alert = AlertDialog.Builder(this)
        alert.setTitle(R.string.select_a_status)
        alert.setSingleChoiceItems(statusList, checkedIndex) { dialog, which ->
            status = Status.entries[which]
        }
        alert.setPositiveButton(R.string.ok) { dialog, which ->
            if (myMovies != null) {
                myMovies!!.Status = status
                myMoviesDAO.update(myMovies!!)
            } else {
                myMovies = MyMovies(
                    status,
                    movie.Title,
                    movie.Year,
                    movie.imdbID,
                    movie.Poster,
                    movie.Plot,
                    movie.Runtime,
                    movie.Director,
                    movie.Genre,
                    movie.Country,
                )
                myMoviesDAO.insert(myMovies!!)
            }
            setSavedIcon()
            loadStatus()
        }
        alert.show()
        loadStatus()
        loadData()
    }
}