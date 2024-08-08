package com.locotoinnovations.composelearnings.repository

import com.locotoinnovations.composelearnings.database.post.PostEntity
import com.locotoinnovations.composelearnings.network.DataResult
import kotlinx.coroutines.flow.Flow

fun interface PostDataSource {
    fun getPosts(): Flow<DataResult<List<PostEntity>>>
}