package com.example.examen.data


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

        @GET("#")
        suspend fun findMoviesByName(
            @Query("s") query: String,
            @Query("apikey") apiKey: String = "fb7aca4"
            ): MovieResponse

        @GET("#")
        suspend fun findMoviesById(@Query("i") id: String,
                                   @Query("apikey") apiKey: String = "fb7aca4"
        ): Movie


    companion object {
        fun getInstance(): MovieService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(MovieService::class.java)
        }
    }
}