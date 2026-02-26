package com.example.prac_five.fireBase.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.util.CoilUtils.result
import com.example.prac_five.fireBase.FirestoreService
import com.example.prac_five.fireBase.FirestoreService.db
import com.example.prac_five.types.MapMarker
import com.example.prac_five.types.User
import com.example.prac_five.types.enums.MarkerType
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun loadUser(uid: String) {
        viewModelScope.launch {
            val result = FirestoreService.getDocument<User>("users", uid)
            result.onSuccess { userFromDb ->
                _user.value = userFromDb
            }
        }
    }



    fun updateUserWithImage(uid: String, newName: String, imageUri: android.net.Uri?) {
        viewModelScope.launch {
            try {
                val updates = mutableMapOf<String, Any>("name" to newName)

                // Если пользователь выбрал новое фото (Uri не null)
                if (imageUri != null) {
                    val downloadUrl = FirestoreService.uploadImage(uid, imageUri)
                    if (downloadUrl != null) {
                        updates["photoURL"] = downloadUrl
                    }
                }

                // Обновляем данные в Firestore
                val result = FirestoreService.updateDocument("users", uid, updates)

                result.onSuccess{
                    loadUser(uid)
                }

            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }


    fun addMarker(latLng: LatLng, user: User) {

        val markerId = db.collection("markers").document().id

        val marker = MapMarker(
            id = markerId,
            lat = latLng.latitude,
            lng = latLng.longitude,
            userId = user.id,
            nickname = user.name,
            avatarUrl = user.photoURL,
            type = "default",
            title = "",
            description = "",
            createdAt = System.currentTimeMillis()
        )

        db.collection("markers")
            .document(markerId)
            .set(marker)
    }


    val _markers = MutableStateFlow<List<MapMarker>>(emptyList())
    val markers: StateFlow<List<MapMarker>> = _markers


    val _favorites = MutableStateFlow<List<String>>(emptyList())
    val favorites: StateFlow<List<String>> = _favorites


    init {
        db.collection("markers")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    val list = it.documents.mapNotNull { doc ->
                        doc.toObject(MapMarker::class.java)
                    }
                    _markers.value = list
                }
            }
    }


    fun updateMarker(markerId: String, title: String, description: String, type: String) {
        db.collection("markers")
            .document(markerId)
            .update(
                mapOf(
                    "title" to title,
                    "description" to description,
                    "type" to type
                )
            )
    }


    fun deleteMarker(markerId: String) {
        db.collection("markers")
            .document(markerId)
            .delete()
    }






    fun toggleFavorite(userId: String, markerId: String, isFavorite: Boolean) {

        val ref = db.collection("users")
            .document(userId)
            .collection("favorites")
            .document(markerId)

        if (isFavorite) {
            ref.delete()
        } else {
            ref.set(
                mapOf(
                    "markerId" to markerId,
                    "addedAt" to System.currentTimeMillis()
                )
            )
        }
    }


    fun listenFavorites(userId: String) {
        db.collection("users")
            .document(userId)
            .collection("favorites")
            .addSnapshotListener { snapshot, _ ->

                val list = snapshot?.documents?.map { it.id } ?: emptyList()
                _favorites.value = list
            }
    }


    // Используем обычный var, так как это просто флаг состояния
    var isFirstLaunch = true
        private set // Изменять можно только внутри ViewModel

    fun markFirstLaunchDone() {
        isFirstLaunch = false
    }

    var lastCameraPosition: CameraPosition? = null

    fun updateCameraPosition(position: CameraPosition) {
        lastCameraPosition = position
    }
}