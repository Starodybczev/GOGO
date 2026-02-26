package com.example.prac_five

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import com.example.prac_five.data.loadDarkTheme
import com.example.prac_five.data.saveDarkTheme
import com.example.prac_five.ui.theme.Prac_fiveTheme
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        FirebaseApp.initializeApp(this)

        setContent {

            val context = LocalContext.current
            var darkTheme by remember { mutableStateOf<Boolean?>(null) }


            LaunchedEffect(Unit) {
                darkTheme = loadDarkTheme(context)
            }

            if (darkTheme != null) {

                Prac_fiveTheme(darkTheme = darkTheme!!) {

                    AppRouter(
                        onToggleTheme = {
                            val newValue = !darkTheme!!
                            darkTheme = newValue

                            lifecycleScope.launch {
                                saveDarkTheme(context, newValue)
                            }
                        }
                    )
                }
            }
        }
    }
}