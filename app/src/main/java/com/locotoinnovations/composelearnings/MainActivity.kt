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
import com.launchdarkly.sdk.android.LDClient
import com.locotoinnovations.composelearnings.featuretoggle.FeatureToggles
import com.locotoinnovations.composelearnings.ui.theme.ComposeLearningsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearningsTheme {
                HomeScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        val isFeatureEnabled = FeatureToggles.LaunchDarkly.SAMPLE_FEATURE.isEnabled()
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(text = "you can see this text $isFeatureEnabled")
            // TODO: call FeatureToggles
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeLearningsTheme {
        HomeScreen()
    }
}