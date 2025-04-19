package com.semenov.testtask.ui.signup

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.semenov.testtask.R
import com.semenov.testtask.ui.theme.black48
import com.semenov.testtask.ui.theme.black60
import com.semenov.testtask.ui.theme.black87
import com.semenov.testtask.ui.theme.blue
import com.semenov.testtask.ui.theme.grey
import com.semenov.testtask.ui.theme.nunitoRegular
import com.semenov.testtask.ui.theme.nunitoSemiBold
import com.semenov.testtask.ui.theme.red
import com.semenov.testtask.ui.theme.yellow
import java.io.File

@Composable
fun RegisterScreen(viewModel: SignUpViewModel = hiltViewModel()) {

    Column {
        TopAppBar()

        when (val state = viewModel.uiState) {

            is RegisterUiState.InitialLoading -> Loading()

            is RegisterUiState.Success -> RegisterDialog(
                icon = painterResource(R.drawable.ic_register_success),
                msg = state.message,
                textBtn = stringResource(R.string.got_it),
                onDismiss = viewModel::resetUiState
            )

            is RegisterUiState.Error -> RegisterDialog(
                icon = painterResource(R.drawable.ic_register_failed),
                state.message,
                textBtn = stringResource(R.string.try_again),
                onDismiss = viewModel::resetUiState
            )

            is RegisterUiState.Form -> FormContent(
                form = state,
                onFieldChange = viewModel::onFieldChange,
                onSubmit = viewModel::onSubmit,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.working_with_post_request),
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
}

@Composable
fun FormContent(
    form: RegisterUiState.Form,
    onFieldChange: (String?, String?, String?, Int?, File?) -> Unit,
    onSubmit: () -> Unit
) {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 16.dp)) {

        InputField(
            value = form.name,
            onValueChange = { onFieldChange(it, null, null, null, null) },
            label = stringResource(R.string.your_name),
            error = form.errors?.name
        )

        InputField(
            value = form.email,
            onValueChange = { onFieldChange(null, it, null, null, null) },
            label = stringResource(R.string.email),
            error = form.errors?.email
        )

        InputField(
            value = form.phone,
            onValueChange = { onFieldChange(null, null, it, null, null) },
            label = stringResource(R.string.phone),
            formatInput = "+38 (XXX) XXX - XX - XX",
            error = form.errors?.phone
        )

        Text(
            stringResource(R.string.select_your_position),
            fontSize = 18.sp,
            fontFamily = nunitoRegular,
            modifier = Modifier.padding(top = 24.dp)
        )

        form.positions.forEach { position ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.selectable(
                    selected = form.positionId == position.id,
                    onClick = { onFieldChange(null, null, null, position.id, null) }
                )
            ) {
                RadioButton(
                    selected = form.positionId == position.id,
                    onClick = { onFieldChange(null, null, null, position.id, null)  },
                    colors = RadioButtonDefaults.colors(blue, blue)
                )
                Text(
                    position.name,
                    fontSize = 16.sp,
                    fontFamily = nunitoRegular)
            }
        }

        form.errors?.position?.let {
            Text(it, color = red, fontSize = 12.sp, modifier = Modifier.padding(start = 16.dp, top = 4.dp))
        }

        UploadPhotoButton(
            photoUri = form.photoFile,
            onPhotoPicked = { onFieldChange(null, null, null, null, it) },
            error = form.errors?.photo,
        )

        Button(
            onClick = { onSubmit() },
            colors = ButtonDefaults.buttonColors(containerColor = yellow),
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(width = 140.dp, height = 48.dp)
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                fontSize = 18.sp,
                fontFamily = nunitoSemiBold,
                color = black87,
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun Loading() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = blue)
    }
}

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    formatInput: String = "",
    error: String? = null
) {
    Column(modifier = Modifier.padding(top = 32.dp)) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = error != null,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = grey,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedLabelColor = black48,
                errorContainerColor = Color.Transparent,
                errorIndicatorColor = red,
                errorLabelColor = red
            )
        )
        if (formatInput.isNotBlank() && error == null) {
            Text(formatInput,
                color = black60,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp))
        }
        if (error != null) {
            Text(error, color = red, fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun UploadPhotoButton(
    photoUri: File?,
    onPhotoPicked: (File) -> Unit,
    error: String?
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            val result = uriToValidatedFile(context, uri)
            result
                .onSuccess { onPhotoPicked(it) }
                .onFailure { error(it.message ?: context.getString(R.string.error_uploading_photo)) }
        }
    }

    val color = if (error != null) red else grey
    val labelText = if (photoUri == null) stringResource(R.string.upload_your_photo) else stringResource(R.string.photo_selected)

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 24.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = color,
                    shape = RoundedCornerShape(4.dp)
                )
                .clickable { launcher.launch("image/jpeg") }
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = labelText, color = color, fontFamily = nunitoRegular, fontSize = 16.sp)
                Text(text = stringResource(R.string.select_photo), color = blue, fontFamily = nunitoRegular, fontSize = 16.sp)
            }
        }
        if (error != null) {
            Text(
                text = error,
                color = red,
                fontSize = 12.sp,
                fontFamily = nunitoRegular,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun RegisterDialog(
    icon: Painter,
    msg: String,
    textBtn: String,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = icon,
            contentDescription = "Icon dialog"
        )
        Text(
            text = msg,
            fontSize = 20.sp,
            modifier = Modifier.padding(24.dp)
        )
        Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(
                containerColor = yellow,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = textBtn,
                fontSize = 18.sp,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

fun uriToValidatedFile(context: Context, uri: Uri): Result<File> {
    val contentResolver = context.contentResolver

    val type = contentResolver.getType(uri)
    if (type != "image/jpeg" && type != "image/jpg") {
        return Result.failure(IllegalArgumentException(context.getString(R.string.the_photo_must_be_in_jpg_or_jpeg_format)))
    }

    val fileSize = contentResolver.openFileDescriptor(uri, "r")?.statSize ?: 0
    if (fileSize > 5 * 1024 * 1024) {
        return Result.failure(IllegalArgumentException(context.getString(R.string.the_photo_size_must_not_exceed_5mb)))
    }

    val inputStream = contentResolver.openInputStream(uri)
    val tempFile = File.createTempFile("photo_upload_", ".jpg", context.cacheDir)
    tempFile.outputStream().use { output ->
        inputStream?.copyTo(output)
    }

    return Result.success(tempFile)
}