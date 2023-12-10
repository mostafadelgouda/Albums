package com.example.albums

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.albums.ui.theme.AlbumsTheme

class MainActivity : ComponentActivity() {
    private val viewModel: UserViewModel by viewModels()
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlbumsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "firstScreen") {
                        composable("firstScreen") {
                            userScreen(userViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), navController)
                        }
                        composable("albumGrid/{albumId}", arguments = listOf(navArgument("albumId") { type = NavType.IntType })) {
                            val albumId = it.arguments?.getInt("albumId") ?: -1
                            AlbumGridScreen(albumId = albumId, userViewModel = androidx.lifecycle.viewmodel.compose.viewModel())
                        }
                    }
                }
            }
        }
    }
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    @Composable
    fun MyApp() {

    }
}



