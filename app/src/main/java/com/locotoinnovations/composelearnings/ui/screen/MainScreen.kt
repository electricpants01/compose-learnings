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
            when (val posts = uiState.posts) {
                is DataResult.Success -> {
                    LazyColumn {
                        items(posts.data.size) { index ->
                            Text(text = posts.data[index].title)
                        }
                    }
                }
                is DataResult.Failure.NetworkError -> {
                    Text(text = "Error: ${posts.message}")
                }

                else -> {
                    // Do nothing, this case is when posts is null
                }
            }
        }

    }
}