package com.locotoinnovations.composelearnings.repository

import com.locotoinnovations.composelearnings.database.post.PostDao
import com.locotoinnovations.composelearnings.database.post.PostEntity
import com.locotoinnovations.composelearnings.database.post.PostPageKeyEntity
import com.locotoinnovations.composelearnings.network.DataResult
import com.locotoinnovations.composelearnings.network.posts.PostService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(
    private val postService: PostService,
    private val postDao: PostDao,
) : PostDataSource {

    override fun fetchPostsAndSaveInLocalStorage(): Flow<DataResult<List<PostEntity>>> = flow {
        try {
            // first, get the data from network and save it to the database
            val postListEntity: List<PostEntity> = postService.fetchPosts(start = 0, limit = 4).map { it.toPostEntity() }
            postDao.insertAll(postListEntity)
            // read the data from the database
            val postList = postDao.getAll()
            // emit the data
            emit(DataResult.Success(postList))
        } catch (e: Exception) {
            val postList = postDao.getAll()
            if (postList.isEmpty()) {
                emit(DataResult.Failure.UnknownError("Unknown error", e))
            } else { // it has some data in the database
                emit(DataResult.Success(postList))
            }
        }
    }

    override suspend fun fetchPosts(start: Int, limit: Int): Result<List<PostEntity>> {
        val response = postService.fetchPosts(start = start, limit = limit)
        return if (response.isNotEmpty()) {
            Result.success(response.map { it.toPostEntity() })
        } else {
            Result.failure(Exception("No posts found"))
        }
    }

    override suspend fun getNextPostPageKey(): Int = postDao.getNextPostPageKey() ?: 0

    override suspend fun insertPostPageKey(pageKey: Int) {
        postDao.insertPostPageKey(PostPageKeyEntity(pageKey))
    }

    override suspend fun savePosts(posts: List<PostEntity>) {
        postDao.insertAll(posts)
    }

    override suspend fun readPostsPagingSource(): androidx.paging.PagingSource<Int, PostEntity> {
        return postDao.readPostsPagingSource()
    }
}