package com.example.completenotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.completenotes.dao.NoteDatabase
import com.example.completenotes.notes.Notes
import com.example.completenotes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val allNotes: LiveData<List<Notes>>
    private val noteRepository: NotesRepository

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        noteRepository = NotesRepository(noteDao)
        allNotes = noteRepository.allNotes
    }

    fun insertNote(note: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
        }
    }

    fun deleteNote(note: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
        }
    }

    fun updateOldNote(note: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.updateOldNode(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteAllNotes()
        }
    }

    fun searchNote(searchQuery: String): LiveData<List<Notes>> {
        return noteRepository.searchNote(searchQuery)
    }
}