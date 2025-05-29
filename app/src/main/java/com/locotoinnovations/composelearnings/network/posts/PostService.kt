package com.locotoinnovations.composelearnings.network.posts

import retrofit2.http.GET
import retrofit2.http.Query

fun interface PostService {
    @GET("posts")
    suspend fun fetchPosts(@Query("_start") start: Int,@Query("_limit") limit: Int): List<PostResponse>
}