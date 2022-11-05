package com.gmail.data.repository.questionrepository

import android.util.Log
import com.gmail.data.repository.COLLECTION
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val TESTS_COLLECTION = "tests"
const val QUESTIONS_COLLECTION = "questions"
const val HINT_COLLECTION = "hints"

class QuestionRepository(private val db: FirebaseFirestore) : QuestionRepositoryInterface {
	override suspend fun getAllTests(): Flow<List<DocumentSnapshot>> = callbackFlow {
		var eventCollection: CollectionReference? = null
		try {
			eventCollection = db.collection(TESTS_COLLECTION)
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

	override suspend fun getHint(hintId: String): Flow<DocumentSnapshot> = callbackFlow{
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

	fun addData(data: Map<String, Any>) {
		db.collection(HINT_COLLECTION)
			.add(data)
			.addOnSuccessListener { documentReference ->
				Log.d(
					"WWW.FirebaseResultAdd",
					"DocumentSnapshot added with ID: " + documentReference.getId()
				);
			}
			.addOnFailureListener {
				Log.w("WWW.FirebaseErrorAdd", "Error adding document ${it.message.toString()}");
			}
	}
}