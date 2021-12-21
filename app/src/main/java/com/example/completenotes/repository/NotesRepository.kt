package com.example.completenotes.repository

import androidx.lifecycle.LiveData
import com.example.completenotes.dao.NoteDao
import com.example.completenotes.notes.Notes

class NotesRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Notes>> = noteDao.getAllNotes()

    suspend fun insertNote(note: Notes){
        noteDao.insertNewNote(note)
    }

    suspend fun deleteNote(note: Notes){
        noteDao.deleteNote(note)
    }

    suspend fun updateOldNode(note: Notes){
        noteDao.updateOldNote(note)
    }

    suspend fun deleteAllNotes(){
        noteDao.deleteAllNotes()
    }

    fun searchNote(searchQuery: String): LiveData<List<Notes>>{
        return noteDao.searchNote(searchQuery)
    }
}