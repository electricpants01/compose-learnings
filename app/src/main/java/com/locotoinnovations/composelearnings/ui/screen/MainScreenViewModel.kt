package com.locotoinnovations.composelearnings.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.locotoinnovations.composelearnings.database.post.PostEntity
import com.locotoinnovations.composelearnings.network.DataResult
import com.locotoinnovations.composelearnings.network.posts.PostRemoteMediator
import com.locotoinnovations.composelearnings.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val posts = Pager(
        config = PagingConfig(
            pageSize = PostRemoteMediator.PAGE_SIZE,
            initialLoadSize = PostRemoteMediator.PAGE_SIZE*3,
            prefetchDistance = 5,
            enablePlaceholders = false
        ),
        remoteMediator = PostRemoteMediator(postRepository),
        pagingSourceFactory = {
            postRepository.readPostsPagingSource()
        }
    ).flow
        .map { pagingData -> pagingData.map { it.toPostUiState() } }
        .cachedIn(viewModelScope)
}