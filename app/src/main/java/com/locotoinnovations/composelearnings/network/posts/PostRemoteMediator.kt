package com.locotoinnovations.composelearnings.network.posts

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.locotoinnovations.composelearnings.database.post.PostEntity
import com.locotoinnovations.composelearnings.repository.PostRepository

/**
 * This remote mediator is responsible for loading posts from a remote source
 * and saving them to the local database.
 * It uses the PostRepository to fetch posts and manage pagination.
 * It handles three types of loads:
 * - REFRESH: Loads the first page of posts and clears the existing data.
 * - APPEND: Loads the next page of posts based on the last loaded page.
 * - PREPEND: Not used in this case, as we are only appending new data.
 * It loads all posts from network until response.size < PAGE_SIZE
 * The first time fetching from the network until the response size is less than PAGE_SIZE
 * the second time the user opens the app, it fetching the the page 0, and last page that is in local storage
 */
@OptIn(ExperimentalPagingApi::class)
class PostRemoteMediator(
    private val postRepository: PostRepository,
) : RemoteMediator<Int, PostEntity>() {

    companion object {
        const val PAGE_SIZE: Int = 5
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val nextKey = postRepository.getNextPostPageKey()
                nextKey
            }
        }

        return try {
            val apiResponse = postRepository.fetchPosts(
                start = page * PAGE_SIZE,
                limit = PAGE_SIZE
            )

            if (apiResponse.isSuccess) {
                val response = apiResponse.getOrDefault(emptyList())

                postRepository.savePosts(response)
                postRepository.insertPostPageKey(page+1)

                val endOfPaginationReached = response.size < PAGE_SIZE

                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                return MediatorResult.Error(apiResponse.exceptionOrNull() ?: Exception("Unknown error"))
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}