package com.locotoinnovations.composelearnings.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.locotoinnovations.composelearnings.network.DataResult

@Composable
fun MainScreen(
    modifier: Modifier,
    viewmodel: MainScreenViewModel = hiltViewModel()
) {

    val uiState by viewmodel.uiState.collectAsStateWithLifecycle(initialValue = MainScreenState())

    Column(
        modifier = modifier,
    ) {
        Button(onClick = {
            viewmodel.fetchPosts()
        }) {
            Text(text = "Fetch data")
        }

        if (uiState.isLoading) {
            Text(text = "Loading...")
        } else {
            when (val todos = uiState.todos) {
                is DataResult.Success -> {
                    LazyColumn {
                        items(todos.data.size) { index ->
                            Text(text = todos.data[index].title)
                        }
                    }
                }
                is DataResult.Failure.NetworkError -> {
                    Text(text = "Error: ${todos.message}")
                }

                else -> {
                    // Do nothing, this case is when posts is null
                }
            }
        }

    }
}