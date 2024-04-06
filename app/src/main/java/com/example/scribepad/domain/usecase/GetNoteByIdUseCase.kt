package com.example.scribepad.domain.usecase

import com.example.scribepad.domain.model.Note
import com.example.scribepad.domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(private val repo: NoteRepository) {

    fun execute(id: Int?): Note {
        return repo.getNoteById(id)
    }
}