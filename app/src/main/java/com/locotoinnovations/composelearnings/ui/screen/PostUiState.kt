package com.locotoinnovations.composelearnings.ui.screen

import androidx.compose.runtime.Immutable
import com.locotoinnovations.composelearnings.database.post.PostEntity

@Immutable
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