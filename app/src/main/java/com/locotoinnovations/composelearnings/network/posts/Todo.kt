package com.locotoinnovations.composelearnings.network.posts

data class Todo(
    val created_at: String,
    val description: String,
    val id: Int,
    val status: String,
    val title: String,
    val updated_at: String
)