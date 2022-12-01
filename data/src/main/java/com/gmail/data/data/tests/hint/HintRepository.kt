package com.gmail.data.data.tests.hint

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HintRepository(private val db: FirebaseFirestore): HintRepositoryInterface {
    companion object{
        const val HINT_COLLECTION = "hints"
    }
    override suspend fun getHint(hintId: String): Flow<DocumentSnapshot> = callbackFlow {
        var eventCollection: DocumentReference? = null
        try {
            eventCollection = db.collection(HINT_COLLECTION).document(hintId)
        } catch (exception: Exception){
            close(exception)
        }
        val subscription = eventCollection
            ?.addSnapshotListener { value, error ->
                if(value == null){
                    return@addSnapshotListener
                }
                if(error != null){
                    return@addSnapshotListener
                }
                trySend(value)
            }
        awaitClose{
            subscription?.remove()
        }
    }
}