package com.example.examen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.examen.R
import com.example.examen.data.MyMovies
import com.example.examen.data.MyMoviesDAO
import com.example.examen.data.Status
import com.example.examen.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MyMoviesAdapter(var items: List<MyMovies>, val onClick: (Int) -> Unit) : Adapter<MyMovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyMovieViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size
    //return items.size



    override fun onBindViewHolder(holder: MyMovieViewHolder, position: Int) {
        val book = items[position]
        holder.render(book)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
    }

    fun updateItems(Items: List<MyMovies>) {
        this.items = Items
        notifyDataSetChanged()
    }
}

class MyMovieViewHolder (val binding: ItemMovieBinding) : ViewHolder(binding.root){

    fun render(movie: MyMovies) {
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
            //binding.statusChip.text = context.getString(myBooks!!.status.title)
            binding.statusChip.visibility = View.VISIBLE
        } else {
            binding.statusChip.visibility = View.GONE
        }
    }

}
