package com.example.prac_five.fireBase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prac_five.fireBase.FirebaseAuthService
import com.example.prac_five.fireBase.FirestoreService
import com.example.prac_five.types.AuthUiState
import com.example.prac_five.types.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _state = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val state = _state.asStateFlow()

    fun register(email: String, password: String, name: String) {

        viewModelScope.launch {

            _state.value = AuthUiState.Loading

            val authResult = FirebaseAuthService.register(email, password)

            authResult.onSuccess { user ->

                val userData = User(
                    uid = user.uid,
                    id = user.uid,
                    email = user.email ?: "",
                    name = name,
                    photoURL = "https://img.freepik.com/free-vector/woman-profile-account-picture_24908-81036.jpg",
                    createdAt = null
                )

                val firestoreResult = FirestoreService.setDocument(
                    collection = "users",
                    documentId = user.uid,
                    data = userData
                )

                firestoreResult.onSuccess {
                    _state.value = AuthUiState.Success
                }.onFailure {
                    _state.value = AuthUiState.Error(it.message ?: "Firestore error")
                }

            }.onFailure {
                _state.value = AuthUiState.Error(it.message ?: "Auth error")
            }
        }
    }


     fun login(email: String, password: String) {

         viewModelScope.launch {
             val result = FirebaseAuthService.login(email, password)

             result.onSuccess {
                 _state.value = AuthUiState.Success
             }.onFailure { e ->

                 val message = when (e) {

                     is FirebaseAuthInvalidUserException ->
                         "User does not exist"

                     is FirebaseAuthInvalidCredentialsException ->
                         "Wrong password"

                     else ->
                         e.message ?: "Login error"
                 }

                 _state.value = AuthUiState.Error(message)
             }
         }
    }


    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}