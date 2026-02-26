package com.example.prac_five.fireBase

import com.example.prac_five.googlemap.model.Place
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import kotlin.jvm.java


object FirestoreService {

     val db = FirebaseFirestore.getInstance()

    suspend fun setDocument(
        collection: String,
        documentId: String,
        data: Any
    ): Result<Unit> {

        return try {
            db.collection(collection)
                .document(documentId)
                .set(data)
                .await()

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addDocument(
        collection: String,
        data: Any
    ): Result<DocumentReference> {

        return try {
            val result = db.collection(collection)
                .add(data)
                .await()

            Result.success(result)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend inline fun <reified T> getDocument(
        collection: String,
        documentId: String
    ): Result<T?> {

        return try {
            val snapshot = db.collection(collection)
                .document(documentId)
                .get()
                .await()

            Result.success(snapshot.toObject(T::class.java))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateDocument(
        collection: String,
        documentId: String,
        updates: Map<String, Any> // Pass a map of fields to change
    ): Result<Unit> = try {
        db.collection(collection).document(documentId).update(updates).await()
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun uploadImage(uid: String, imageUri: android.net.Uri): String? {
        val storageRef = FirebaseStorage.getInstance().reference.child("avatars/$uid.jpg")
        return try {
            storageRef.putFile(imageUri).await()
            storageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            null
        }
    }


    object FirestoreService {

        suspend fun getPlaces(): Result<List<Place>> = try {
            val snap = db.collection("default").get().await()
            val places = snap.documents.map { doc ->
                val p = doc.toObject(Place::class.java) ?: Place()
                p.copy(id = doc.id)
            }
            Result.success(places)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}