package com.gmail.data.repository

import android.util.Log
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val COLLECTION = "psyhologists"

class FirebaseRepository() : RepositoryInterface {
    private val db = Firebase.firestore

    override suspend fun getAllData(): Flow<List<DocumentSnapshot>> = callbackFlow {
        try {
            db.collection(COLLECTION)
                .get(Source.SERVER)
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("WWW.FirestoreResult ${document.id}", document.data.toString())
                    }
                }
                .addOnFailureListener {
                    Log.w("WWW.FirestoreError", it.message.toString())
                }
            awaitClose { return@awaitClose }
        } catch (exception: Exception) {
            close(exception)
        }

    }


    override suspend fun getItemData(idPsychologist: String): Flow<DocumentSnapshot> =
        callbackFlow {
            var eventCollection: DocumentReference? = null
            try {
                eventCollection = db.collection(COLLECTION)
                    .document(idPsychologist)
            } catch (exception: Exception) {
                close(exception)
            }
            val subscription =
                eventCollection?.addSnapshotListener { value, _ ->
                    if (value == null) {
                        return@addSnapshotListener
                    }
                    trySend(value)
                }
            awaitClose {
                subscription?.remove()
            }
        }


    override suspend fun addData(data: Map<String, Any>): Flow<Any> = callbackFlow {
        try {
            db.collection(COLLECTION)
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.d(
                        "WWW.FirebaseResultAdd",
                        "DocumentSnapshot added with ID: " + documentReference.id
                    );
                }
                .addOnFailureListener {
                    Log.w("WWW.FirebaseErrorAdd", "Error adding document ${it.message.toString()}");
                }
            awaitClose { return@awaitClose }
        } catch (exception: Exception) {
            close(exception)
        }
    }
}
