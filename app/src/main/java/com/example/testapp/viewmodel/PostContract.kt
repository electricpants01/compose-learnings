package com.example.testapp.viewmodel

import com.example.testapp.data.model.Post

data class PostUiState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList(),
    val selectedPost: Post? = null,
    val errorMessage: String? = null
)

sealed interface PostEvent {
    object LoadPosts : PostEvent
    data class LoadPostDetail(val postId: Int) : PostEvent
    data class OnPostClicked(val postId: Int) : PostEvent
    object OnBackClicked : PostEvent
}

sealed interface PostSideEffect {
    data class NavigateToDetail(val postId: Int) : PostSideEffect
    object NavigateBack : PostSideEffect
}
