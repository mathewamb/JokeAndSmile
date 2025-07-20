package com.example.jokeandsmile.service


import com.example.jokeandsmile.model.Joke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface JokeApi {
    @GET("random_joke")
    suspend fun getRandomJoke(): Joke

    companion object {
        fun create(): JokeApi {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl("https://official-joke-api.appspot.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(JokeApi::class.java)
        }
    }
}
