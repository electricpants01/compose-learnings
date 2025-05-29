package com.locotoinnovations.composelearnings.network.posts

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.locotoinnovations.composelearnings.database.post.PostEntity
import com.locotoinnovations.composelearnings.repository.PostRepository

@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val postRepository: PostRepository,
): RemoteMediator<Int, PostEntity>() {

    private var endReached = false

    companion object {
        private val PAGE_SIZE = 5
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        if (endReached) return MediatorResult.Success(endOfPaginationReached = true)

        val page = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val nextKey = postRepository.getNextPostPageKey() ?: return MediatorResult.Success(endOfPaginationReached = true)
                nextKey
            }
        }

        return try {
            val apiResponse = postRepository.fetchPosts(
                start = (page * PAGE_SIZE),
                limit = PAGE_SIZE
            )

            if (apiResponse.isSuccess) {
                val response = apiResponse.getOrDefault(emptyList())
                postRepository.savePosts(response)
                postRepository.insertPostPageKey(page+1)

                if (response.size < PAGE_SIZE) {
                    endReached = true
                }

                MediatorResult.Success(endOfPaginationReached = endReached)
            } else {
                return MediatorResult.Error(apiResponse.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}