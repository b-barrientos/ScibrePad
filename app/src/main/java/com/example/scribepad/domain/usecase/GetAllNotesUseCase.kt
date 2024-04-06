package com.example.scribepad.domain.usecase

import com.example.scribepad.domain.model.Note
import com.example.scribepad.domain.repository.NoteRepository
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(private val repo: NoteRepository) {
    fun execute(): List<Note> {
        return repo.getAllNotes()
    }
}