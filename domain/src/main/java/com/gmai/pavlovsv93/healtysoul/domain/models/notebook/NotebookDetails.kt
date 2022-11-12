package com.gmai.pavlovsv93.healtysoul.domain.models.notebook

data class NotebookDetails(
    val isLoadingDone: Boolean = false,
    val listNotes: List<Notebook> = emptyList()
)