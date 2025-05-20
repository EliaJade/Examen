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
    val Status: String

    )
{

    companion object {
        const val TABLE_NAME = "MyBooks"

        const val COLUMN_BOOK_STATUS = "status"

        const val COLUMN_BOOK_TITLE = "title"
        const val COLUMN_BOOK_YEAR = "year"
        const val COLUMN_BOOK_IMDBID = "imdbid"
        const val COLUMN_BOOK_POSTER = "poster"
        const val COLUMN_BOOK_PLOT = "plot"
        const val COLUMN_BOOK_RUNTIME = "runtime"
        const val COLUMN_BOOK_DIRECTOR = "director"
        const val COLUMN_BOOK_GENRE = "genre"
        const val COLUMN_BOOK_COUNTRY = "country"

    }
}