package com.gmail.data.repository.questionrepository

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class QuestionsRepository(private val db: FirebaseFirestore) : QuestionsRepositoryInterface {

	companion object{
		const val QUESTIONS_COLLECTION = "questions"
	}

	override suspend fun getQuestion(questionId: String): Flow<DocumentSnapshot> =
		callbackFlow {
			var eventCollection: CollectionReference? = null
			try {
				eventCollection = db.collection(QUESTIONS_COLLECTION)
			} catch (exception: Exception){
				close(exception)
			}
			val subscription = eventCollection
				?.document(questionId)
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