package com.locotoinnovations.composelearnings.repository

import com.locotoinnovations.composelearnings.database.post.PostEntity
import com.locotoinnovations.composelearnings.network.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postDataSourceImpl: PostDataSourceImpl,
) {

    fun readPosts(): Flow<DataResult<List<PostEntity>>> = postDataSourceImpl.fetchPostsAndSaveInLocalStorage()

    suspend fun fetchPosts(start: Int, limit: Int): Result<List<PostEntity>> =
        postDataSourceImpl.fetchPosts(start, limit)

    suspend fun savePosts(posts: List<PostEntity>) = postDataSourceImpl.savePosts(posts)

    fun readPostsPagingSource() = postDataSourceImpl.readPostsPagingSource()

    suspend fun getNextPostPageKey(): Int = postDataSourceImpl.getNextPostPageKey()

    suspend fun insertPostPageKey(pageKey: Int) = postDataSourceImpl.insertPostPageKey(pageKey)
}