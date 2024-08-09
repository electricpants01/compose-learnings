package com.locotoinnovations.composelearnings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locotoinnovations.composelearnings.ui.compoennt.FixedHorizontalGridScreen
import com.locotoinnovations.composelearnings.ui.compoennt.FixedVerticalGridScreen
import com.locotoinnovations.composelearnings.ui.theme.ComposeLearningsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearningsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        FixedVerticalGridScreen(modifier = Modifier.weight(1f).padding(innerPadding))
                        FixedHorizontalGridScreen(modifier = Modifier.weight(1f).padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    ComposeLearningsTheme {
        Column {
            FixedVerticalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
            FixedHorizontalGridScreen(modifier = Modifier.weight(1f).padding(4.dp))
        }
    }
}