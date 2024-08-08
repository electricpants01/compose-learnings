package com.locotoinnovations.composelearnings.ui.screen

import com.locotoinnovations.composelearnings.network.DataResult
import com.locotoinnovations.composelearnings.network.posts.Post
import com.locotoinnovations.composelearnings.network.posts.PostService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postService: PostService
) {

    fun getPosts(): Flow<DataResult<List<Post>>> = flow {
        try {
            val posts = postService.getPosts()
            emit(DataResult.Success(posts))
        } catch (e: Exception) {
            emit(DataResult.Failure.NetworkError("Network error", e))
        }
    }
}