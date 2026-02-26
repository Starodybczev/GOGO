package com.example.prac_five.pages

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.prac_five.R
import com.example.prac_five.components.HeaderSettings
import com.example.prac_five.constElems.AppButton
import com.example.prac_five.constElems.Gap
import com.example.prac_five.constElems.Input
import com.example.prac_five.constElems.marking.AppColumn
import com.example.prac_five.fireBase.viewmodel.UserViewModel
import com.example.prac_five.types.FormStateRegister
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UpdateProfile(
    navController: NavHostController,
    formStateAuth: FormStateRegister,
    onFormChange: (FormStateRegister) -> Unit
) {

    val currentUser = remember { FirebaseAuth.getInstance().currentUser }
    val uid = currentUser?.uid
    val viewModel: UserViewModel = viewModel()
    val user by viewModel.user.collectAsState()


    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                onFormChange(formStateAuth.copy(photoUri = uri))
            }
        }
    )

    val isEnebled = formStateAuth.name.isNotBlank()


    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = { HeaderSettings(navController) },
    ) { paddingValues ->
        AppColumn(
            modifier = Modifier.padding(paddingValues),
        )
        {
            Text("UpdateProfile", color = MaterialTheme.colorScheme.onBackground)
            Gap()
            AsyncImage(
                model = user?.photoURL,
                contentDescription = "User Photo",
                placeholder = painterResource(R.drawable.basic_denied_reject), // Покажи что-то пока грузится
                error = painterResource(R.drawable.basic_denied_reject),       // Покажи если ссылка битая
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )
            Gap()
            AppButton(text = "Выбрать фото", onClick = {
                photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            })

            Input(value = formStateAuth.name, onValueChange = {onFormChange(formStateAuth.copy(name = it))}, label = "new name")
            AppButton(text = "update", onClick = {if (uid != null){viewModel.updateUserWithImage(uid = uid, newName = formStateAuth.name, imageUri = formStateAuth.photoUri)}}, enabled = isEnebled)
        }
    }
}