package com.gmail.pavlovsv93.healthysoul.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.data.repository.questionrepository.QuestionRepository
import com.gmail.pavlovsv93.healthysoul.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import my.addHint


class HealthySoulActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
//		val data = addHint()
//		val db = Firebase.firestore
//		QuestionRepository(db).addData(data)
	}
}