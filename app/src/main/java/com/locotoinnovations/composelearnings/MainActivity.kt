package com.locotoinnovations.composelearnings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.locotoinnovations.composelearnings.ui.components.MainHorizontalScreen
import com.locotoinnovations.composelearnings.ui.components.MainVerticalScreen
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
                        MainVerticalScreen(modifier = Modifier.weight(1f).padding(innerPadding))
                        MainHorizontalScreen(modifier = Modifier.weight(1f).padding(innerPadding))
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeLearningsTheme {
        Column {
            MainVerticalScreen(modifier = Modifier.weight(1f).padding(4.dp))
            MainHorizontalScreen(modifier = Modifier.weight(1f).padding(4.dp))
        }
    }
}