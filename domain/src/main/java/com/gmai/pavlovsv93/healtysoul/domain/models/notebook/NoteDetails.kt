package com.gmai.pavlovsv93.healtysoul.domain.models.notebook

data class NoteDetails(
    val isLoadingDone: Boolean = false,
    val deleteNote: Boolean = false,
    val getNote: Boolean = false,
    val saveNote: Boolean = false,
    val updateNote: Boolean = false,
    val note:Notebook = Notebook()
)
