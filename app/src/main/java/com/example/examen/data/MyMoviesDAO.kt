package com.example.examen.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.examen.utils.DatabaseManager

class MyMoviesDAO(context: Context) {
    val databaseManager = DatabaseManager(context)

    fun insert(myMovies: MyMovies) {

        val db = databaseManager.writableDatabase

        val values = ContentValues().apply {
            put(MyMovies.COLUMN_MOVIE_STATUS, myMovies.Status.ordinal)
            put(MyMovies.COLUMN_MOVIE_TITLE, myMovies.Title)
            put(MyMovies.COLUMN_MOVIE_YEAR, myMovies.Year)
            put(MyMovies.COLUMN_MOVIE_IMDBID, myMovies.imdbID)
            put(MyMovies.COLUMN_MOVIE_POSTER, myMovies.Poster)
            put(MyMovies.COLUMN_MOVIE_PLOT, myMovies.Plot)
            put(MyMovies.COLUMN_MOVIE_RUNTIME, myMovies.Runtime)
            put(MyMovies.COLUMN_MOVIE_DIRECTOR, myMovies.Director)
            put(MyMovies.COLUMN_MOVIE_GENRE, myMovies.Genre)
            put(MyMovies.COLUMN_MOVIE_COUNTRY, myMovies.Country)
        }

        try {
            val newRowId = db.insert(MyMovies.TABLE_NAME, null, values)
            Log.i("DATABASE", "Inserted book with id: $newRowId")
        } catch (e:Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }
    fun update(myMovies: MyMovies) {

        val db = databaseManager.writableDatabase

        val values = ContentValues().apply {
            put(MyMovies.COLUMN_MOVIE_STATUS, myMovies.Status.ordinal)
            put(MyMovies.COLUMN_MOVIE_TITLE, myMovies.Title)
            put(MyMovies.COLUMN_MOVIE_YEAR, myMovies.Year)
            put(MyMovies.COLUMN_MOVIE_IMDBID, myMovies.imdbID)
            put(MyMovies.COLUMN_MOVIE_POSTER, myMovies.Poster)
            put(MyMovies.COLUMN_MOVIE_PLOT, myMovies.Plot)
            put(MyMovies.COLUMN_MOVIE_RUNTIME, myMovies.Runtime)
            put(MyMovies.COLUMN_MOVIE_DIRECTOR, myMovies.Director)
            put(MyMovies.COLUMN_MOVIE_GENRE, myMovies.Genre)
            put(MyMovies.COLUMN_MOVIE_COUNTRY, myMovies.Country)
        }
        try {
            val updatedRows  = db.update(MyMovies.TABLE_NAME, values, "${MyMovies.COLUMN_MOVIE_IMDBID} = '${myMovies.imdbID}'", null)
            Log.i("DATABASE", "Updated movies with id: $updatedRows")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }


    fun delete(myMovies: MyMovies) {
        val db = databaseManager.writableDatabase

        try {
            val deletedRows = db.delete(MyMovies.TABLE_NAME, "${MyMovies.COLUMN_MOVIE_IMDBID} = '${myMovies.imdbID}'", null)
            Log.i("DATABASE", "Deleted movies with id: $deletedRows")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }
    fun findById(id: String): MyMovies? {
        val db = databaseManager.readableDatabase
        val projection = arrayOf(
            MyMovies.COLUMN_MOVIE_STATUS,
            MyMovies.COLUMN_MOVIE_TITLE,
            MyMovies.COLUMN_MOVIE_YEAR,
            MyMovies.COLUMN_MOVIE_IMDBID,
            MyMovies.COLUMN_MOVIE_POSTER,
            MyMovies.COLUMN_MOVIE_PLOT,
            MyMovies.COLUMN_MOVIE_RUNTIME,
            MyMovies.COLUMN_MOVIE_DIRECTOR,
            MyMovies.COLUMN_MOVIE_GENRE,
            MyMovies.COLUMN_MOVIE_COUNTRY
        )

        val selection = "${MyMovies.COLUMN_MOVIE_IMDBID} = '$id'"

        var movie: MyMovies? = null

        try {
            val cursor = db.query(
                MyMovies.TABLE_NAME,       //THE TABLE TO QUERY
                projection,         //THE ARRAY OF COLUMNS TO RETURN ( PASS NULL TO GET ALL)
                selection,          //THE COLUMNS FOR THE where CLAUSE
                null,   //THE VALUES FOR THE where CLAUSE
                null,       //DON'T GROUP THE ROWS
                null,        // DON'T FILTER BY ROW GROUPS
                null        // THE SORT ORDER
            )


            if (cursor.moveToNext()) {
                val status = cursor.getInt(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_STATUS))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_TITLE))
                val year = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_YEAR))
                val itemId = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_IMDBID))
                val poster = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_POSTER))
                val plot = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_PLOT))
                val runtime = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_RUNTIME))
                val director = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_DIRECTOR))
                val genre = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_GENRE))
                val country = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_COUNTRY))


                movie = MyMovies(Status.entries[status], title, year, itemId, poster, plot, runtime, director, genre, country)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return movie
    }

    fun findAll(): List<MyMovies> {
        val db = databaseManager.readableDatabase
        val projection = arrayOf(
            MyMovies.COLUMN_MOVIE_STATUS,
            MyMovies.COLUMN_MOVIE_TITLE,
            MyMovies.COLUMN_MOVIE_YEAR,
            MyMovies.COLUMN_MOVIE_IMDBID,
            MyMovies.COLUMN_MOVIE_POSTER,
            MyMovies.COLUMN_MOVIE_PLOT,
            MyMovies.COLUMN_MOVIE_RUNTIME,
            MyMovies.COLUMN_MOVIE_DIRECTOR,
            MyMovies.COLUMN_MOVIE_GENRE,
            MyMovies.COLUMN_MOVIE_COUNTRY
        )

        val movieList: MutableList<MyMovies> = mutableListOf()

        try {
            val cursor = db.query(
                MyMovies.TABLE_NAME,       //THE TABLE TO QUERY
                projection,             //THE ARRAY OF COLUMNS TO RETURN ( PASS NULL TO GET ALL)
                null,          //THE COLUMNS FOR THE where CLAUSE
                null,       //THE VALUES FOR THE where CLAUSE
                null,           //DON'T GROUP THE ROWS
                null,            // DON'T FILTER BY ROW GROUPS
                null            // THE SORT ORDER
            )


            while (cursor.moveToNext()) {
                val status = cursor.getInt(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_STATUS))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_TITLE))
                val year = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_YEAR))
                val itemId = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_IMDBID))
                val poster = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_POSTER))
                val plot = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_PLOT))
                val runtime = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_RUNTIME))
                val director = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_DIRECTOR))
                val genre = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_GENRE))
                val country = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_COUNTRY))

                val movie = MyMovies(Status.entries[status], title, year, itemId, poster, plot, runtime, director, genre, country)
                movieList.add(movie)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return movieList
    }

    fun findByMyMovieName(query: String, status: Status?): List<MyMovies> {
        val db = databaseManager.readableDatabase
        val projection = arrayOf(
            MyMovies.COLUMN_MOVIE_STATUS,
            MyMovies.COLUMN_MOVIE_TITLE,
            MyMovies.COLUMN_MOVIE_YEAR,
            MyMovies.COLUMN_MOVIE_IMDBID,
            MyMovies.COLUMN_MOVIE_POSTER,
            MyMovies.COLUMN_MOVIE_PLOT,
            MyMovies.COLUMN_MOVIE_RUNTIME,
            MyMovies.COLUMN_MOVIE_DIRECTOR,
            MyMovies.COLUMN_MOVIE_GENRE,
            MyMovies.COLUMN_MOVIE_COUNTRY
        )


        val movieList: MutableList<MyMovies> = mutableListOf()

        var selection = if (status != null) {
            "${MyMovies.COLUMN_MOVIE_STATUS} = ${status.ordinal} AND "
        } else {
            ""
        }
        selection += "${MyMovies.COLUMN_MOVIE_TITLE} LIKE '%$query%'"

        try {
            val cursor = db.query(
                MyMovies.TABLE_NAME,       //THE TABLE TO QUERY
                projection,             //THE ARRAY OF COLUMNS TO RETURN ( PASS NULL TO GET ALL)
                selection,          //THE COLUMNS FOR THE where CLAUSE
                null,       //THE VALUES FOR THE where CLAUSE
                null,           //DON'T GROUP THE ROWS
                null,            // DON'T FILTER BY ROW GROUPS
                null            // THE SORT ORDER
            )


            while (cursor.moveToNext()) {
                val status = cursor.getInt(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_STATUS))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_TITLE))
                val year = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_YEAR))
                val itemId = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_IMDBID))
                val poster = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_POSTER))
                val plot = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_PLOT))
                val runtime = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_RUNTIME))
                val director = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_DIRECTOR))
                val genre = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_GENRE))
                val country = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_MOVIE_COUNTRY))


                val movie = MyMovies(Status.entries[status], title, year, itemId, poster, plot, runtime, director, genre, country)
                movieList.add(movie)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return movieList
    }
}