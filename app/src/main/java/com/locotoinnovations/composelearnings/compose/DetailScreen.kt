package com.locotoinnovations.composelearnings.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.locotoinnovations.composelearnings.DetailViewModel

@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
) {
    Text("Show detail screen")
}