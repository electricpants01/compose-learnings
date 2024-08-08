package com.locotoinnovations.composelearnings.repository

import com.locotoinnovations.composelearnings.database.post.PostDao
import com.locotoinnovations.composelearnings.database.post.PostEntity
import com.locotoinnovations.composelearnings.network.DataResult
import com.locotoinnovations.composelearnings.network.posts.PostService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(
    private val postService: PostService,
    private val postDao: PostDao,
) : PostDataSource {

    override fun getPosts(): Flow<DataResult<List<PostEntity>>> = flow {
        try {
            // first, get the data from network and save it to the database
            val postListEntity: List<PostEntity> = postService.getPosts().map { it.toPostEntity() }
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
}