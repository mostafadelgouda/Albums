package com.example.albums

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.albums.ui.theme.AlbumsTheme
import java.time.format.TextStyle


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun userScreen(userViewModel: UserViewModel = viewModel(), navController: NavController) {
    var isEditing by remember { mutableStateOf(false) }
    var newUserId by remember { mutableStateOf("") }
    val user by userViewModel.userId
    val users by userViewModel.users
    val userId by userViewModel.userId

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(10.dp),
    ) {
        if (isEditing) {
            TextField(
                value = newUserId,
                onValueChange = {
                    newUserId = it
                },
                label = { Text("Enter User ID") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Button(onClick = {
                userViewModel.changeUser(newUserId.toIntOrNull() ?: userId)
                isEditing = false
            }) {
                Text("Confirm")
            }
        } else {
            Text(
                text = "Profile",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            if (users.isNotEmpty()) {
                Text(text = users[userId].name)

                Button(onClick = {
                    isEditing = true
                    newUserId = userId.toString()
                }) {
                    Text("Edit User ID")
                }

                if (users.isNotEmpty()) {

                    Text(
                        text = "Address: ${users[user].address.street}, ${users[user].address.suite}," +
                                "${users[user].address.city}, ${users[user].address.zipcode}"
                    )

                    AlbumList(userId = users[user].id, viewModel = userViewModel, navController)
                }
            }
        }

    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun AlbumList(userId: Int, viewModel: UserViewModel, navController: NavController) {
    val albums by viewModel.albums

    LaunchedEffect(userId) {
        viewModel.getAlbums(userId)
    }

    for (album in albums) {
        val selectedAlbumId = album.id
        Box(
            modifier = Modifier
                .clickable {
                    navController.navigate("albumGrid/$selectedAlbumId")
                }
                .fillMaxWidth()
                .height(50.dp)
                .drawBehind {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 1.dp.toPx()
                    )
                }
        ) {
            Text(
                text = AnnotatedString(album.title),
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}
