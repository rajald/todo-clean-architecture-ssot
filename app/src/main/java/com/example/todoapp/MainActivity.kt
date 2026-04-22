package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.navigation.AppNavHost
import com.example.todoapp.ui.theme.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TodoAppTheme {
                AppNavHost(
                    navController = navController
                )
                //ArticleListScreen(modifier = Modifier.padding(16.dp))
            }
        }
    }
}