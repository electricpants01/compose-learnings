package com.example.testapp.data.remote

import com.example.testapp.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }
}
