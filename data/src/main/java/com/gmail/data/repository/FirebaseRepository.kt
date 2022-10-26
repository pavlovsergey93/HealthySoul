package com.gmail.data.repository

import android.util.Log
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val COLLECTION = "psyhologists"

class FirebaseRepository() {
	private val db = Firebase.firestore

	fun getData() {
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

	}

	fun addData(data: Map<String, Any>) {
		db.collection(COLLECTION)
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
