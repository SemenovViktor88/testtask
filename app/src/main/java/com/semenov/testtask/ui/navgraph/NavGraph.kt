package com.semenov.testtask.ui.navgraph

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import com.semenov.testtask.ui.signup.RegisterScreen
import com.semenov.testtask.ui.signup.SignUpViewModel
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
            val viewModel: SignUpViewModel = hiltViewModel()
            RegisterScreen(viewModel)
        }
    }
}

sealed class Screen(val route: String, @StringRes val title: Int, @DrawableRes val icon: Int) {
    data object Users : Screen("users", R.string.users, R.drawable.ic_users)
    data object Register : Screen("register", R.string.sign_up, R.drawable.ic_register)
}