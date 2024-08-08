package com.locotoinnovations.composelearnings.repository

import com.locotoinnovations.composelearnings.database.post.PostEntity
import com.locotoinnovations.composelearnings.network.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postDataSourceImpl: PostDataSourceImpl,
) {

    fun getPosts(): Flow<DataResult<List<PostEntity>>> = postDataSourceImpl.getPosts()

}