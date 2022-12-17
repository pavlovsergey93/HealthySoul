package com.gmail.data.repository.psychologist

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PsychologistRepository(private val db: FirebaseFirestore) : PsychologistRepositoryInterface {
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
}
