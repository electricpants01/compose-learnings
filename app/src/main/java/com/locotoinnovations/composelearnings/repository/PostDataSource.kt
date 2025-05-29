package com.locotoinnovations.composelearnings.repository

import androidx.paging.PagingSource
import com.locotoinnovations.composelearnings.database.post.PostEntity
import com.locotoinnovations.composelearnings.database.post.PostPageKeyEntity
import com.locotoinnovations.composelearnings.network.DataResult
import kotlinx.coroutines.flow.Flow

interface PostDataSource {
    fun fetchPostsAndSaveInLocalStorage(): Flow<DataResult<List<PostEntity>>>

    suspend fun fetchPosts(start: Int, limit: Int): Result<List<PostEntity>>
    suspend fun savePosts(posts: List<PostEntity>)
    suspend fun readPostsPagingSource(): PagingSource<Int, PostEntity>
    suspend fun getNextPostPageKey(): Int
    suspend fun insertPostPageKey(pageKey: Int)
}