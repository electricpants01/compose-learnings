package com.locotoinnovations.composelearnings.ui.compoennt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
fun FixedVerticalGridScreen(
    modifier: Modifier,
) {
    val itemList = (0..100).map { "Item $it" }
    var columns by remember {
        mutableStateOf(GridCells.Fixed(2))
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = { columns = GridCells.Fixed(1) }) {
                Text(text = "1 Columns")
            }
            Button(onClick = { columns = GridCells.Fixed(2) }) {
                Text(text = "2 Columns")
            }
            Button(onClick = { columns = GridCells.Fixed(3) }) {
                Text(text = "3 Columns")
            }
        }
        LazyVerticalGrid(columns = columns) {
            items(itemList.size) { index ->
                Text(text = itemList[index], modifier = Modifier.padding(4.dp).background(colors.random()))
            }
        }
    }
}

@Composable
fun FixedHorizontalGridScreen(
    modifier: Modifier,
) {
    val itemList = (0..100).map { "Item $it" }
    var rows by remember {
        mutableStateOf(GridCells.Fixed(2))
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = { rows = GridCells.Fixed(1) }) {
                Text(text = "1 Columns")
            }
            Button(onClick = { rows = GridCells.Fixed(2) }) {
                Text(text = "2 Columns")
            }
            Button(onClick = { rows = GridCells.Fixed(3) }) {
                Text(text = "3 Columns")
            }
        }
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
        FixedVerticalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
        FixedHorizontalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
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
        FixedVerticalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
        FixedHorizontalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
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
        FixedVerticalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
        FixedHorizontalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
    }
}