package com.locotoinnovations.composelearnings.ui.compoennt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// create random colors
private val colors = List(1000) {
    Color(
        red = (0..255).random(),
        green = (0..255).random(),
        blue = (0..255).random()
    )
}

@Composable
fun FixedSizeVerticalGridScreen(
    modifier: Modifier,
) {
    val itemList = (0..100).map { "Item $it" }
    val columns by remember {
        mutableStateOf(GridCells.FixedSize(size = 150.dp))
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(columns = columns) {
            items(itemList.size) { index ->
                Text(text = itemList[index], modifier = Modifier.padding(4.dp).background(colors.random()))
            }
        }
    }
}

@Composable
fun FixedSizeHorizontalGridScreen(
    modifier: Modifier,
) {
    val itemList = (0..100).map { "Item $it" }
    val rows by remember {
        mutableStateOf(GridCells.FixedSize(size = 120.dp))
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyHorizontalGrid(rows = rows) {
            items(itemList.size) { index ->
                Text(text = itemList[index], modifier = Modifier.padding(4.dp).background(colors.random()))
            }
        }
    }
}

@Preview(
    name = "Phone Preview",
    widthDp = 360,
    heightDp = 640
)
@Composable
private fun PhonePreview() {
    Column {
        FixedSizeVerticalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
        FixedSizeHorizontalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
    }
}

@Preview(
    name = "Tablet Preview",
    widthDp = 800,
    heightDp = 1280
)
@Composable
private fun TablePreview() {
    Column {
        FixedSizeVerticalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
        FixedSizeHorizontalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
    }
}

@Preview(
    name = "Large Tablet Preview",
    widthDp = 1280,
    heightDp = 800
)
@Composable
private fun LargeTablePreview() {
    Column {
        FixedSizeVerticalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
        FixedSizeHorizontalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
    }
}