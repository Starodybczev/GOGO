package com.example.prac_five.fireBase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

object FirebaseAuthService {

    private val auth = FirebaseAuth.getInstance()

    fun currentUser(): FirebaseUser? = auth.currentUser

    suspend fun login(
        email: String,
        password: String
    ): Result<FirebaseUser> {

        return try {

            val result = auth
                .signInWithEmailAndPassword(email, password)
                .await()

            val user = result.user ?: throw Exception("User null")

            Result.success(user)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(
        email: String,
        password: String
    ): Result<FirebaseUser> {

        return try {
            val result = auth
                .createUserWithEmailAndPassword(email, password)
                .await()

            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }
}