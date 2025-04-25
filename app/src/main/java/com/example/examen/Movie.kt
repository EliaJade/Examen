package com.example.examen

import com.google.gson.annotations.SerializedName

data class MovieResponse (
    @SerializedName("Search") val movie: List<Movie>
)

data class Movie (
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Poster: String,
    val Plot: String,
    val Runtime: String,
    val Director: String,
    val Genre: String,
    val Country: String,

)

class poster (val url: String){
}