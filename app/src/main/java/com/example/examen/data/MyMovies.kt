package com.example.examen.data


class MyMovies(
    val Title: String,
    val Year: String,
    val imdbID: String,
    val Poster: String,
    val Plot: String,
    val Runtime: String,
    val Director: String,
    val Genre: String,
    val Country: String,
    val Status: Status

    )
{

    companion object {
        const val TABLE_NAME = "MyBooks"

        const val COLUMN_MOVIE_STATUS = "status"

        const val COLUMN_MOVIE_TITLE = "title"
        const val COLUMN_MOVIE_YEAR = "year"
        const val COLUMN_MOVIE_IMDBID = "imdbid"
        const val COLUMN_MOVIE_POSTER = "poster"
        const val COLUMN_MOVIE_PLOT = "plot"
        const val COLUMN_MOVIE_RUNTIME = "runtime"
        const val COLUMN_MOVIE_DIRECTOR = "director"
        const val COLUMN_MOVIE_GENRE = "genre"
        const val COLUMN_MOVIE_COUNTRY = "country"

    }
}