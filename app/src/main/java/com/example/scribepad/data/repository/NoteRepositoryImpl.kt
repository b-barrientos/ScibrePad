package com.example.scribepad.data.repository

import com.example.scribepad.data.database.NoteDao
import com.example.scribepad.domain.model.Note
import com.example.scribepad.domain.repository.NoteRepository

class NoteRepositoryImpl(private val noteDao: NoteDao) : NoteRepository {
    override fun getNoteById(id: Int?): Note {
        return noteDao.findNoteById(id)
    }

    override fun getAllNotes(): List<Note> {
        return noteDao.getAllNotes()
    }

    override fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override fun updateNote(note: Note) {
        noteDao.updateNote(note)
    }
}