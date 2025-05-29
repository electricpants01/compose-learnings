package com.locotoinnovations.composelearnings.ui.screen

import com.locotoinnovations.composelearnings.database.post.PostEntity

data class PostUiState(
    val id: Int,
    val title: String,
    val body: String,
)

fun PostEntity.toPostUiState(): PostUiState {
    return PostUiState(
        id = this.id,
        title = "${this.id} - ${this.title}",
        body = this.body
    )
}