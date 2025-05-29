package com.locotoinnovations.composelearnings.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locotoinnovations.composelearnings.database.post.PostEntity
import com.locotoinnovations.composelearnings.network.DataResult
import com.locotoinnovations.composelearnings.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val postRepository: PostRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState: Flow<MainScreenUiState> = _uiState

    fun fetchPosts() {
        postRepository.getPosts()
            .onStart {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        posts = null
                    )
                }
            }
            .catch { ex ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = ex.message ?: "Error fetching posts"
                    )
                }
            }
            .onEach { postList ->
                when (postList) {
                    is DataResult.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                posts = postList.data.map { it.toPostUiState() }
                            )
                        }
                    }
                    is DataResult.Failure.NetworkError -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = postList.message ?: "Network error"
                            )
                        }
                    }
                    else -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = "Unknown error"
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }
}

data class MainScreenUiState(
    val isLoading: Boolean = true,
    val posts: List<PostUiState>? = null,
    val error: String? = null

)