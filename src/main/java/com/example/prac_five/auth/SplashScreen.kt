package com.example.prac_five.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

@Composable
fun SplashScreen(navController: NavHostController) {


    LaunchedEffect(Unit) {

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            try {
                user.reload().await()

                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }

            } catch (e: Exception) {
                FirebaseAuth.getInstance().signOut()

                navController.navigate("auth") {
                    popUpTo("splash") { inclusive = true }
                }
            }

        } else {
            navController.navigate("auth") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }
}