package com.example.scribepad.presentation.notescreen

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.example.scribepad.domain.model.Note
import com.example.scribepad.domain.usecase.GetNoteByIdUseCase
import com.example.scribepad.domain.usecase.InsertNoteUseCase
import com.example.scribepad.domain.usecase.UpdateNoteUseCase
import com.pluto.plugins.logger.PlutoLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.Date
import javax.inject.Inject

@HiltViewModel
class NoteScreenViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val insertNoteUseCase: InsertNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase
) : ViewModel() {

    private val _note = MutableStateFlow<Note?>(null)
    val note = _note.asStateFlow()

    private val _navigate = MutableStateFlow(false)
    val navigate = _navigate.asStateFlow()

    private val _noteColor = MutableStateFlow(Note.noteColors.random().toArgb())
    val noteColor = _noteColor.asStateFlow()

    private var currentNoteId: Int? = null

    fun getNote(noteId: Int) {
        if (noteId != -1) {
            CoroutineScope(Dispatchers.IO).launch {
                _note.value = getNoteByIdUseCase.execute(noteId)
                currentNoteId = noteId
                PlutoLog.d("note_screen", "note fetched with id: $noteId")
            }
        }
    }

    fun insertOrUpdateNote(addNote: Boolean, noteTitle: String, noteContent: String, noteColor: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            if (addNote) {
                insertNoteUseCase.execute(
                    note = Note(
                        id = currentNoteId,
                        noteTitle = noteTitle,
                        noteContent = noteContent,
                        noteColor = noteColor,
                        dateLastModified = Date(System.currentTimeMillis())
                    )
                )
                _navigate.emit(true)
                PlutoLog.d("note_screen", "added new note with id: $currentNoteId")
            } else {
                updateNoteUseCase.execute(
                    note = Note(
                        id = currentNoteId,
                        noteTitle = noteTitle,
                        noteContent = noteContent,
                        noteColor = noteColor,
                        dateLastModified = Date(System.currentTimeMillis())
                    )
                )
                _navigate.emit(true)
                PlutoLog.d("note_screen", "updated note with id: $currentNoteId")
            }
        }
    }
}