package com.locotoinnovations.composelearnings.network.posts

import retrofit2.http.GET

fun interface PostService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}