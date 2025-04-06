package com.semenov.testtask.ui.navgraph

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.semenov.testtask.R
import com.semenov.testtask.ui.register.RegisterScreen
import com.semenov.testtask.ui.register.RegisterViewModel
import com.semenov.testtask.ui.users.UsersScreen
import com.semenov.testtask.ui.users.UsersViewModel

@Composable
fun NavGraph(navController: NavController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.Users.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(Screen.Users.route) {
            val viewModel: UsersViewModel = hiltViewModel()
            UsersScreen(viewModel)
        }
        composable(Screen.Register.route) {
            val viewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(viewModel)
        }
    }
}

sealed class Screen(val route: String, val title: String, @DrawableRes val icon: Int) {
    data object Users : Screen("users", "Users", R.drawable.ic_users)
    data object Register : Screen("register", "Sign up", R.drawable.ic_register)
}