package com.semenov.testtask.ui.mainscreen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.semenov.testtask.ui.bottombar.BottomNavigationBar
import com.semenov.testtask.ui.navgraph.NavGraph
import com.semenov.testtask.ui.nointernet.NoInternetScreen

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    val isConnected by mainViewModel.isConnected.collectAsState()

    if (!isConnected) {
        NoInternetScreen {}
    } else {
        Scaffold(
            containerColor = Color.White,
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            NavGraph(navController, paddingValues)
        }
    }
}