package com.example.testapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import com.example.testapp.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    private val _sideEffect = MutableSharedFlow<PostSideEffect>()
    val sideEffect: SharedFlow<PostSideEffect> = _sideEffect.asSharedFlow()

    init {
        onEvent(PostEvent.LoadPosts)
    }

    fun onEvent(event: PostEvent) {
        when (event) {
            is PostEvent.LoadPosts -> fetchPosts()
            is PostEvent.LoadPostDetail -> fetchPostDetail(event.postId)
            is PostEvent.OnPostClicked -> {
                viewModelScope.launch {
                    _sideEffect.emit(PostSideEffect.NavigateToDetail(event.postId))
                }
            }
            is PostEvent.OnBackClicked -> {
                viewModelScope.launch {
                    _sideEffect.emit(PostSideEffect.NavigateBack)
                }
            }
        }
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            repository.getPosts()
                .onSuccess { posts ->
                    _uiState.update { it.copy(isLoading = false, posts = posts) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
        }
    }

    private fun fetchPostDetail(postId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, selectedPost = null) }
            repository.getPostById(postId)
                .onSuccess { post ->
                    _uiState.update { it.copy(isLoading = false, selectedPost = post) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
                }
        }
    }

}
