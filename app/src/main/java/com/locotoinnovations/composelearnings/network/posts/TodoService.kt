package com.locotoinnovations.composelearnings.network.posts

import retrofit2.http.GET

fun interface TodoService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>
}