package com.locotoinnovations.composelearnings.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locotoinnovations.composelearnings.network.DataResult
import com.locotoinnovations.composelearnings.network.posts.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState: Flow<MainScreenState> = _uiState

    fun fetchPosts() {
        todoRepository.getTodos()
            .onEach { todoList ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        todos = todoList
                    )
                }
            }.launchIn(viewModelScope)
    }
}

data class MainScreenState(
    val isLoading: Boolean = true,
    val todos: DataResult<List<Todo>>? = null,
)