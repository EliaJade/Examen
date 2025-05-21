package com.example.examen.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION_CODES.O
import android.util.Log
import com.example.examen.data.MyMovies

class DatabaseManager(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            const val DATABASE_NAME = "myMovies.db"
            const val DATABASE_VERSION = 1
            private const val SQL_CREATE_MYMOVIE_MYMOVIE =
                "CREATE TABLE ${MyMovies.TABLE_NAME} (" +
                        "${MyMovies.COLUMN_MOVIE_IMDBID} TEXT PRIMARY KEY," +
                        "${MyMovies.COLUMN_MOVIE_STATUS} INTEGER," +
                        "${MyMovies.COLUMN_MOVIE_TITLE} INTEGER," +
                        "${MyMovies.COLUMN_MOVIE_YEAR} INTEGER," +
                        "${MyMovies.COLUMN_MOVIE_POSTER} INTEGER," +
                        "${MyMovies.COLUMN_MOVIE_PLOT} INTEGER," +
                        "${MyMovies.COLUMN_MOVIE_RUNTIME} INTEGER," +
                        "${MyMovies.COLUMN_MOVIE_DIRECTOR} INTEGER," +
                        "${MyMovies.COLUMN_MOVIE_GENRE} INTEGER," +
                        "${MyMovies.COLUMN_MOVIE_COUNTRY} INTEGER)"

            private const val SQL_DROP_MYMOVIE_MYMOVIE = "DROP TABLE IF EXISTS ${MyMovies.TABLE_NAME}"
        }
        override fun onCreate(db: SQLiteDatabase) {O


            db.execSQL(SQL_CREATE_MYMOVIE_MYMOVIE)
            Log.i("DATABASE", "Created table Task")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onDestroy(db)
            onCreate(db)
        }

        private fun onDestroy(db: SQLiteDatabase) {
            db.execSQL(SQL_DROP_MYMOVIE_MYMOVIE)
        }
    }
