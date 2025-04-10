package com.semenov.testtask.ui.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.semenov.testtask.R
import com.semenov.testtask.ui.theme.black87

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_no_users),
            contentDescription = "No Users",
            modifier = Modifier
                .padding(bottom = 24.dp)
                .size(200.dp),
            tint = Color.Unspecified
        )
        Text(stringResource(R.string.there_are_no_users_yet), fontSize = 20.sp, color = black87)
    }
}

@Preview(showSystemUi = true)
@Composable
fun EmptyStatePreview() {
    EmptyState()
}