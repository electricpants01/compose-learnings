package com.example.testapp.data.repository

import com.example.testapp.data.model.Post
import com.example.testapp.data.remote.PostApiService
import javax.inject.Inject

interface PostRepository {
    suspend fun getPosts(): Result<List<Post>>
    suspend fun getPostById(id: Int): Result<Post>
}

class PostRepositoryImpl @Inject constructor(private val apiService: PostApiService) : PostRepository {
    override suspend fun getPosts(): Result<List<Post>> {
        return try {
            val posts = apiService.getPosts()
            Result.success(posts)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPostById(id: Int): Result<Post> {
        return try {
            val post = apiService.getPostById(id)
            Result.success(post)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
