package com.semenov.testtask.ui.nointernet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.semenov.testtask.R
import com.semenov.testtask.ui.theme.yellow

@Composable
fun NoInternetScreen(retryAction: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_no_internet),
            contentDescription = "Icon no internet"
        )
        Text(
            stringResource(R.string.no_internet_connection),
            fontSize = 20.sp,
            modifier = Modifier.padding(24.dp))
        Button(
            onClick = retryAction,
            colors = ButtonDefaults.buttonColors(
                containerColor = yellow,
                contentColor = Color.Black)
            ) {
            Text(
                stringResource(R.string.try_again),
                fontSize = 18.sp,
                modifier = Modifier.padding(12.dp))
        }
    }
}