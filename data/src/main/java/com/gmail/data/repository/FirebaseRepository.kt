package com.gmail.data.repository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRepository(private val db: FirebaseFirestore) :
    RepositoryInterface {
    companion object {
        private const val COLLECTION = "psyhologists"
    }

    override suspend fun getAllData(): Flow<List<DocumentSnapshot>> = callbackFlow {
        var eventCollection: CollectionReference? = null
        try {
            eventCollection = db.collection(COLLECTION)
        } catch (exception: Exception) {
            close(exception)
        }
        val subscription = eventCollection
            ?.addSnapshotListener { value, error ->
                if (value == null) {
                    return@addSnapshotListener
                }
                if (error != null) {
                    return@addSnapshotListener
                }
                trySend(value.documents)
            }
        awaitClose {
            subscription?.remove()
        }
    }

    override suspend fun getItemData(idPsychologist: String): Flow<DocumentSnapshot> {
        TODO("Not yet implemented")
    }

    override suspend fun addData(data: Map<String, Any>): Flow<Any> {
        TODO("Not yet implemented")
    }

//    override suspend fun getAllData(): Flow<List<DocumentSnapshot>> = callbackFlow {
//        try {
//            db.collection(COLLECTION)
//                .get(Source.SERVER)
//                .addOnSuccessListener { documents ->
//                    for (document in documents) {
//                        Log.d("WWW.FirestoreResult ${document.id}", document.data.toString())
//                    }
//                }
//                .addOnFailureListener {
//                    Log.w("WWW.FirestoreError", it.message.toString())
//                }
//            awaitClose { return@awaitClose }
//        } catch (exception: Exception) {
//            close(exception)
//        }
//    }

//    override suspend fun getItemData(idPsychologist: String): Flow<DocumentSnapshot> =
//        callbackFlow {
//            var eventCollection: DocumentReference? = null
//            try {
//                eventCollection = db.collection(COLLECTION)
//                    .document(idPsychologist)
//            } catch (exception: Exception) {
//                close(exception)
//            }
//            val subscription =
//                eventCollection?.addSnapshotListener { value, _ ->
//                    if (value == null) {
//                        return@addSnapshotListener
//                    }
//                    trySend(value)
//                }
//            awaitClose {
//                subscription?.remove()
//            }
//        }

//    override suspend fun addData(data: Map<String, Any>): Flow<Any> = callbackFlow {
//        try {
//            db.collection(COLLECTION)
//                .add(data)
//                .addOnSuccessListener { documentReference ->
//                    Log.d(
//                        "WWW.FirebaseResultAdd",
//                        "DocumentSnapshot added with ID: " + documentReference.id
//                    );
//                }
//                .addOnFailureListener {
//                    Log.w("WWW.FirebaseErrorAdd", "Error adding document ${it.message.toString()}");
//                }
//            awaitClose { return@awaitClose }
//        } catch (exception: Exception) {
//            close(exception)
//        }
//    }
}
