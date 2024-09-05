package com.locotoinnovations.composelearnings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
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
import com.locotoinnovations.composelearnings.ui.theme.ComposeLearningsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeLearningsTheme {
                var expanded by remember { mutableStateOf(false) }
                var selectedOption by remember { mutableStateOf("Select an option") }

                Scaffold(
//                    floatingActionButton = {
//                        FloatingActionButton(onClick = { expanded = !expanded }) {
//                            Text("+")
//                        }
//                    },
//                    floatingActionButtonPosition = FabPosition.End
                ) { paddingValues ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        // Content of the screen
                        Text(
                            text = selectedOption,
                            modifier = Modifier.padding(paddingValues)
                                .clickable {
                                    expanded = !expanded
                                }
                        )

                        // Box around DropdownMenu to position it near the FAB
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .background(Color.Red)
                        ) {
                            if (expanded) {
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    DropdownMenuItem(
                                        onClick = {
                                            selectedOption = "Option 1"
                                            expanded = false
                                        }, text = {
                                            Text(text = "Option 1")
                                        })

                                    DropdownMenuItem(
                                        onClick = {
                                            selectedOption = "Option 2"
                                            expanded = false
                                        }, text = {
                                            Text(text = "Option 2")
                                        })

                                    DropdownMenuItem(
                                        onClick = {
                                            selectedOption = "Option 3"
                                            expanded = false
                                        }, text = {
                                            Text(text = "Option 3")
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}