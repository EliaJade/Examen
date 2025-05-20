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
            put(MyMovies.COLUMN_BOOK_STATUS, myMovies.Status.ordinal)
            put(MyMovies.COLUMN_BOOK_TITLE, myMovies.Title)
            put(MyMovies.COLUMN_BOOK_YEAR, myMovies.Year)
            put(MyMovies.COLUMN_BOOK_IMDBID, myMovies.imdbID)
            put(MyMovies.COLUMN_BOOK_POSTER, myMovies.Poster)
            put(MyMovies.COLUMN_BOOK_PLOT, myMovies.Plot)
            put(MyMovies.COLUMN_BOOK_RUNTIME, myMovies.Runtime)
            put(MyMovies.COLUMN_BOOK_DIRECTOR, myMovies.Director)
            put(MyMovies.COLUMN_BOOK_GENRE, myMovies.Genre)
            put(MyMovies.COLUMN_BOOK_COUNTRY, myMovies.Country)
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
            put(MyMovies.COLUMN_BOOK_STATUS, myMovies.Status.ordinal)
            put(MyMovies.COLUMN_BOOK_TITLE, myMovies.Title)
            put(MyMovies.COLUMN_BOOK_YEAR, myMovies.Year)
            put(MyMovies.COLUMN_BOOK_IMDBID, myMovies.imdbID)
            put(MyMovies.COLUMN_BOOK_POSTER, myMovies.Poster)
            put(MyMovies.COLUMN_BOOK_PLOT, myMovies.Plot)
            put(MyMovies.COLUMN_BOOK_RUNTIME, myMovies.Runtime)
            put(MyMovies.COLUMN_BOOK_DIRECTOR, myMovies.Director)
            put(MyMovies.COLUMN_BOOK_GENRE, myMovies.Genre)
            put(MyMovies.COLUMN_BOOK_COUNTRY, myMovies.Country)
        }
        try {
            val updatedRows  = db.update(MyMovies.TABLE_NAME, values, "${MyMovies.COLUMN_BOOK_IMDBID} = '${myMovies.imdbID}'", null)
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
            val deletedRows = db.delete(MyMovies.TABLE_NAME, "${MyMovies.COLUMN_BOOK_IMDBID} = '${myMovies.imdbID}'", null)
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
            MyMovies.COLUMN_BOOK_IMDBID,
            MyMovies.COLUMN_BOOK_STATUS,
            MyMovies.COLUMN_BOOK_TITLE,
            MyMovies.COLUMN_BOOK_YEAR,
            MyMovies.COLUMN_BOOK_IMDBID,
            MyMovies.COLUMN_BOOK_POSTER,
            MyMovies.COLUMN_BOOK_PLOT,
            MyMovies.COLUMN_BOOK_RUNTIME,
            MyMovies.COLUMN_BOOK_DIRECTOR,
            MyMovies.COLUMN_BOOK_GENRE,
            MyMovies.COLUMN_BOOK_COUNTRY
        )

        val selection = "${MyMovies.COLUMN_BOOK_IMDBID} = '$id'"

        var book: MyMovies? = null

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
                val itemId = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_IMDBID))
                val status = cursor.getInt(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_STATUS))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_TITLE))
                val year = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_YEAR))
                val thumbnail = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_IMDBID))
                val thumbnail = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_IMDBID))
                val thumbnail = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_IMDBID))
                val thumbnail = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_IMDBID))
                val thumbnail = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_IMDBID))
                val thumbnail = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_IMDBID))


                book = MyMovies(itemId, Status.entries[status], title, year, thumbnail)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }

        return book
    }

    fun findAll(): List<MyMovies> {
        val db = databaseManager.readableDatabase
        val projection = arrayOf(
            MyMovies.COLUMN_BOOK_ID,
            MyMovies.COLUMN_BOOK_STATUS,
            MyMovies.COLUMN_BOOK_TITLE,
            MyMovies.COLUMN_BOOK_AUTHOR,
            MyMovies.COLUMN_BOOK_THUMBNAIL,
        )

        val bookList: MutableList<MyMovies> = mutableListOf()

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
                val itemId = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_ID))
                val status = cursor.getInt(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_STATUS))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_TITLE))
                val author = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_AUTHOR))?.split("; ")
                val thumbnail = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_THUMBNAIL))


                val book = MyMovies(itemId, Status.entries[status], title, author, thumbnail)
                bookList.add(book)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return bookList
    }

    fun findByMyBookName(query: String, status: Status?): List<MyMovies> {
        val db = databaseManager.readableDatabase
        val projection = arrayOf(
            MyMovies.COLUMN_BOOK_ID,
            MyMovies.COLUMN_BOOK_STATUS,
            MyMovies.COLUMN_BOOK_TITLE,
            MyMovies.COLUMN_BOOK_AUTHOR,
            MyMovies.COLUMN_BOOK_THUMBNAIL,
        )


        val bookList: MutableList<MyMovies> = mutableListOf()

        var selection = if (status != null) {
            "${MyMovies.COLUMN_BOOK_STATUS} = ${status.ordinal} AND "
        } else {
            ""
        }
        selection += "${MyMovies.COLUMN_BOOK_TITLE} LIKE '%$query%'"

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
                val itemId = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_ID))
                val status = cursor.getInt(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_STATUS))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_TITLE))
                val author = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_AUTHOR))?.split("; ")
                val thumbnail = cursor.getString(cursor.getColumnIndexOrThrow(MyMovies.COLUMN_BOOK_THUMBNAIL))


                val book = MyMovies(itemId, Status.entries[status], title, author, thumbnail)
                bookList.add(book)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.close()
        }
        return bookList
    }
}