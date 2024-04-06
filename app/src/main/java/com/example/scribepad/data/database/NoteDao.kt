package com.example.scribepad.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.scribepad.domain.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note where  id LIKE :id")
    fun findNoteById(id: Int?): Note

    @Query("SELECT * FROM note")
    fun getAllNotes(): List<Note>

    @Update
    fun updateNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}