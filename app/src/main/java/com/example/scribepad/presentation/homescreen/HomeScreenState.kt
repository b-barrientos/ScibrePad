package com.example.scribepad.presentation.homescreen

import com.example.scribepad.domain.model.Note

data class HomeScreenState(
    val notes: List<Note> = emptyList()
)
