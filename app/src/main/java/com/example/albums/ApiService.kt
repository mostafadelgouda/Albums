package com.example.albums

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// used retrofit to get the data from the website
val retrofit = Retrofit.Builder()
    .baseUrl("https://jsonplaceholder.typicode.com")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi::class.java)

interface JsonPlaceholderApi {

    @GET("/users")
    suspend fun getUsers(): List<User>

    @GET("/albums")
    suspend fun getAlbums(@Query("userId") userId: Int): List<Album>

    @GET("/photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): List<Photo>
}
