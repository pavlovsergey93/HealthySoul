package com.gmail.data.repository.tests.testscategory

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class TestsCategoryRepository(private val db: FirebaseFirestore) :
    TestsCategoryRepositoryInterface {
    companion object {
        private const val CATEGORY_COLLECTION = "category"
    }

    override suspend fun getListCategory(): Flow<List<DocumentSnapshot>> = callbackFlow {
        var eventCollection: CollectionReference? = null
        try {
            eventCollection = db.collection(CATEGORY_COLLECTION)
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
}