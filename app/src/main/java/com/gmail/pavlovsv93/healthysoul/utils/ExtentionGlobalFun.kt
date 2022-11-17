package com.gmail.pavlovsv93.healthysoul.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showMessage(
	str: String,
	length: Int = Snackbar.LENGTH_SHORT
) {
	Snackbar.make(this, str, length).show()
}

fun View.showMessage(
	str: String,
	length: Int = Snackbar.LENGTH_SHORT,
	actionText: String,
	action: (View) -> Unit
) {
	Snackbar.make(this, str, length)
		.setAction(actionText, action)
		.show()
}