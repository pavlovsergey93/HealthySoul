package com.gmail.data.data.tests.hint

import com.gmail.data.repository.questionrepository.QuestionsRepository
import com.google.firebase.firestore.CollectionReference
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
        var eventCollection: CollectionReference? = null
        try {
            eventCollection = db.collection(HINT_COLLECTION)
        } catch (exception: Exception){
            close(exception)
        }
        val subscription = eventCollection
            ?.document(hintId)
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