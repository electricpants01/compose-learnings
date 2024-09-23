package com.locotoinnovations.composelearnings.ui.screen

import com.locotoinnovations.composelearnings.network.DataResult
import com.locotoinnovations.composelearnings.network.posts.Todo
import com.locotoinnovations.composelearnings.network.posts.TodoService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoService: TodoService
) {

    fun getTodos(): Flow<DataResult<List<Todo>>> = flow {
        try {
            val posts = todoService.getTodos()
            emit(DataResult.Success(posts))
        } catch (e: Exception) {
            emit(DataResult.Failure.NetworkError("Network error", e))
        }
    }
}