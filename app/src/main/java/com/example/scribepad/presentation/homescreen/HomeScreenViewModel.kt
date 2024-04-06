package com.example.scribepad.presentation.homescreen

import androidx.lifecycle.ViewModel
import com.example.scribepad.domain.model.Note
import com.example.scribepad.domain.usecase.DeleteNoteUseCase
import com.example.scribepad.domain.usecase.GetAllNotesUseCase
import com.pluto.plugins.logger.PlutoLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val getNoteUseCase: GetAllNotesUseCase, private val deleteNoteUseCase: DeleteNoteUseCase): ViewModel() {
    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _deleted = MutableStateFlow(false)
    val deleted = _deleted.asStateFlow()

    init {
        getAllNotes()
    }

    fun getAllNotes() {
       CoroutineScope(Dispatchers.IO).launch {
           _state.value = state.value.copy(notes = getNoteUseCase.execute())
           _deleted.value = false
           PlutoLog.d("home_screen", "all notes fetched")
           }
       }


    fun deleteNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteNoteUseCase.execute(note)
            _deleted.value = true
            PlutoLog.d("home_screen", "delete note button clicked")
        }
    }
}