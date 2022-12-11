package com.gmail.data.repository_implementation.notebook

import android.annotation.SuppressLint
import com.firebase.ui.auth.AuthUI
import com.gmai.pavlovsv93.healtysoul.domain.models.notebook.Notebook
import com.gmai.pavlovsv93.healtysoul.domain.repository.notebook.NotebookRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NotebookFirebaseRepositoryImplementation : NotebookRepository {
    @SuppressLint("RestrictedApi")
    private val userId = AuthUI.getInstance().auth.currentUser!!.uid
    private val database = Firebase.firestore.collection(userId)

    override fun deleteNote(note: Notebook): Flow<Boolean> = flow {
        database
            .document(note.id.toString())
            .delete()
        emit(true)
    }

    override fun getAllNotes(): Flow<List<Notebook>> = flow {
        val notebookList = mutableListOf<Notebook>()
        database
            .get()
            .addOnSuccessListener {
                for (doc in it) {
                    notebookList.add(doc.toObject())
                }
            }
        delay(4000)
        emit(notebookList)
    }

    override fun getNote(id: Int): Flow<Notebook> = flow {
        var temp = Notebook(-1)
        database
            .document(id.toString())
            .get()
            .addOnSuccessListener {
                val notebook = it.toObject<Notebook>()
                temp = notebook ?: Notebook(-1)
            }
        delay(2000)
        emit(temp)
    }

    override fun saveNote(note: Notebook): Flow<Boolean> = flow {
        database
            .document(note.id.toString())
            .set(note)
        delay(2000)
        emit(true)
    }

    override fun updateNote(note: Notebook): Flow<Boolean> = flow {
        database
            .document(note.id.toString())
            .update(
                mapOf(
                    "title" to note.title,
                    "content" to note.content
                )
            )
        delay(2000)
        emit(true)
    }
}