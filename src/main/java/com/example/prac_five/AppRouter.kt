package com.example.prac_five

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.prac_five.auth.AuthScreen
import com.example.prac_five.auth.Login
import com.example.prac_five.auth.SplashScreen
import com.example.prac_five.components.Footer
import com.example.prac_five.pages.About
import com.example.prac_five.pages.Favorites
import com.example.prac_five.pages.LogOut
import com.example.prac_five.pages.Settings
import com.example.prac_five.pages.ThemeColor
import com.example.prac_five.pages.UpdateProfile
import com.example.prac_five.types.FormStateRegister


@Composable
fun AppRouter(onToggleTheme: () -> Unit) {
    val navController = rememberNavController()


    var formStateAuth by remember { mutableStateOf(FormStateRegister()) }


    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        NavHost(
            startDestination = "splash", navController = navController, modifier = Modifier
                .fillMaxSize()


        ) {

            composable("splash") {
                SplashScreen(navController)
            }

            composable("auth") {
                AuthScreen(
                    formStateAuth = formStateAuth,
                    onFormChange = { formStateAuth = it },
                    navController
                )
            }

            composable("login") {
                Login(formStateAuth, navController, onFormChange = { formStateAuth = it })
            }

            composable("home") {
                App(navController)
            }
            composable("about") {
                About(navController)
            }
            composable("favorites") {
                Favorites(navController)
            }
            composable("settings") {
                Settings(navController)
            }


            composable("theme") {
                ThemeColor(navController, onToggleTheme)
            }
            composable("logout") {
                LogOut(navController)
            }
            composable("updateProfile") {
                UpdateProfile(navController, formStateAuth, onFormChange = { formStateAuth = it })
            }

        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        if (currentRoute in listOf("home", "favorites")) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp)
            ) {
                Footer(navController)
            }
        }
    }
}
