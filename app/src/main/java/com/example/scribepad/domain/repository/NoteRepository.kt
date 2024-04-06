package com.example.scribepad.domain.repository

import com.example.scribepad.domain.model.Note

interface NoteRepository {
    fun getNoteById(id: Int?) : Note
    fun getAllNotes() : List<Note>
    fun insertNote(note: Note)
    fun deleteNote(note: Note)
    fun updateNote(note: Note)
}