package com.example.prac_five.auth

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.prac_five.constElems.AppButton
import com.example.prac_five.constElems.AppLink
import com.example.prac_five.constElems.Input
import com.example.prac_five.constElems.marking.AppColumn
import com.example.prac_five.fireBase.viewmodel.AuthViewModel
import com.example.prac_five.types.AuthUiState
import com.example.prac_five.types.FormStateRegister

@Composable
fun Login(
    formStateAuth: FormStateRegister,
    navController: NavHostController,
    onFormChange: (FormStateRegister) -> Unit
) {
    val viewModel: AuthViewModel = viewModel()
    val state by viewModel.state.collectAsState()

    var submitClicked by remember { mutableStateOf(false) }

    LaunchedEffect(state) {
        if (state is AuthUiState.Success) {
            navController.navigate("home") {
                popUpTo("auth") { inclusive = true }
            }
        }
    }

    val localError = if (submitClicked) {
        when {
            formStateAuth.password.length < 6 ->
                "Password must be at least 6 characters"

            formStateAuth.password != formStateAuth.configurePassword ->
                "Passwords do not match"

            else -> null
        }
    } else null


    AppColumn(modifier = Modifier.fillMaxSize()) {
        Text("Login" , color = MaterialTheme.colorScheme.onBackground)
        Input(value = formStateAuth.email, onValueChange = {onFormChange(formStateAuth.copy(email = it))}, label = "enter email")
        Input(value = formStateAuth.password, onValueChange = {onFormChange(formStateAuth.copy(password = it))}, label = "enter password")
        if (localError != null) {
            Text(
                text = localError,
                color = Color.Red
            )
        }

        if (state is AuthUiState.Error) {
            Text(
                text = (state as AuthUiState.Error).message,
                color = Color.Red
            )
        }

        AppButton(text = "login", onClick = {submitClicked = true; if (localError == null) viewModel.login(formStateAuth.email, formStateAuth.password)})

        AppLink(text = "register", onClick = {navController.navigate("auth")})
    }
}