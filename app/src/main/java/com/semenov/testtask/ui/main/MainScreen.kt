package com.semenov.testtask.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.semenov.testtask.Greeting
import com.semenov.testtask.ui.nointernet.NoInternetScreen

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    name: String,
    modifier: Modifier = Modifier
) {
    val isConnected by mainViewModel.isConnected.collectAsState()

    if (isConnected) {
        Greeting(name, modifier)
    } else {
        NoInternetScreen {}
    }
}