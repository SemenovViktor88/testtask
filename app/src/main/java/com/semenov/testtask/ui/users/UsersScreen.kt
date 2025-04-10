package com.semenov.testtask.ui.users

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.semenov.testtask.R
import com.semenov.testtask.model.AppUser
import com.semenov.testtask.ui.theme.black60
import com.semenov.testtask.ui.theme.nunitoRegular
import com.semenov.testtask.ui.theme.yellow
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(viewModel: UsersViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val isInitialLoading by viewModel.isInitialLoading.collectAsState()
    val isPaginating by viewModel.isPaginating.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
            val totalItems = layoutInfo.totalItemsCount
            lastVisibleItem?.index == totalItems - 1
        }.distinctUntilChanged()
            .collect { isAtEnd ->
                if (isAtEnd) {
                    viewModel.loadUsers()
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.working_with_get_request),
                    color = Color.Black,
                    fontFamily = nunitoRegular,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = yellow
            ),
            windowInsets = WindowInsets(0.dp)
        )

        if (state.users.isEmpty() && !isInitialLoading) {
            EmptyState()
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isInitialLoading && state.users.isEmpty()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.users) { user ->
                            UserItem(user)
                            HorizontalDivider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier.padding(start = 82.dp, end = 16.dp)
                            )
                        }

                        if (isPaginating) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun UserItem(user: AppUser) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(vertical = 8.dp),
    ) {
        if (user.photo.isNullOrBlank()) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Default Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                tint = Color.Gray
            )
        } else {
            AsyncImage(
                model = user.photo,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                alignment = Alignment.TopCenter
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                user.name,
                fontFamily = nunitoRegular,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                user.position,
                fontFamily = nunitoRegular,
                fontSize = 14.sp,
                color = black60,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                user.email,
                fontFamily = nunitoRegular,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                user.phone,
                fontFamily = nunitoRegular,
                fontSize = 14.sp,
                modifier = Modifier.height(20.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ItemPreview(){
    UserItem(
        AppUser(
            id = 123456,
            name = "Viktor Semenov",
            email = "viktor@gmail.com",
            phone = "+380989831511",
            position = "developer",
            positionId = 1,
            registrationTimestamp = 123456789,
            photo = ""
        )
    )
}