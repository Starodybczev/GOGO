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
fun AuthScreen(
    formStateAuth: FormStateRegister,
    onFormChange: (FormStateRegister) -> Unit,
    navController: NavHostController
) {
    var submitClicked by remember { mutableStateOf(false) }
    val viewModel: AuthViewModel = viewModel()
    val state by viewModel.state.collectAsState()

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
        Text("Register" , color = MaterialTheme.colorScheme.onBackground)
        Input(value = formStateAuth.name, onValueChange = {onFormChange(formStateAuth.copy(name = it))}, label = "enter name")
        Input(value = formStateAuth.email, onValueChange = {onFormChange(formStateAuth.copy(email = it))}, label = "enter email")
        Input(value = formStateAuth.password, onValueChange = {onFormChange(formStateAuth.copy(password = it))}, label = "enter password")
        Input(value =  formStateAuth.configurePassword, onValueChange = {onFormChange(formStateAuth.copy(configurePassword = it))}, label = "enter configure password")
        if (localError != null) {
            Text(
                text = localError,
                color = Color.Red
            )
        }

        AppButton(text = "register", onClick = {submitClicked = true ; if (localError == null) {viewModel.register(formStateAuth.email, formStateAuth.password, formStateAuth.name)} } )

        AppLink(text = "login", onClick = {navController.navigate("login")})

    }
}