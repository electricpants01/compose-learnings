package com.locotoinnovations.composelearnings.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainVerticalScreen(
    modifier: Modifier,
) {
    // mocked data
    val itemList = (0..100).map { "Item $it" }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(itemList) { item ->
            Text(text = item)
        }
    }
}

@Composable
fun MainHorizontalScreen(
    modifier: Modifier,
) {
    val itemList = (0..100).map { "Item $it" }

    LazyRow(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(itemList) { item ->
            Text(text = item)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainVerticalScreen(
        modifier = Modifier,
    )
}

@Preview(showSystemUi = true)
@Composable
fun MainHorizontalScreenPreview() {
    MainHorizontalScreen(
        modifier = Modifier,
    )
}