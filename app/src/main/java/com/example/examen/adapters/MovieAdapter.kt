package com.example.examen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.examen.R
import com.example.examen.data.Movie
import com.example.examen.data.MyMovies
import com.example.examen.data.MyMoviesDAO
import com.example.examen.data.Status
import com.example.examen.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieAdapter(var items: List<Movie>, val onClick: (Int) -> Unit) : Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = items[position]
        holder.render(movie)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }
}

class MovieViewHolder(val binding: ItemMovieBinding) : ViewHolder(binding.root) {

    fun render(movie: Movie) {
        binding.titleTextView.text = movie.Title
        binding.YearTextView.text = movie.Year
        Picasso.get().load(movie.Poster).into(binding.movieImageView)
        loadStatus(movie.imdbID)
    }

    private fun loadStatus(id: String) {
        val context = itemView.context
        val myMovies = MyMoviesDAO(context).findById(id)
        if (myMovies != null) {
            val iconId = when(myMovies.Status!!) {
                Status.WATCHED -> R.drawable.watched_ic
                Status.UNFINISHED -> R.drawable.unfinished_ic
                Status.WANT_TO_WATCH -> R.drawable.want_to_watch_ic
            }
            binding.statusChip.setChipIconResource(iconId)
            binding.statusChip.visibility = View.VISIBLE
        } else {
            binding.statusChip.visibility = View.GONE
        }
    }
}