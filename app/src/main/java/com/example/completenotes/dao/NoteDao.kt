package com.example.completenotes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.completenotes.notes.Notes

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes_table ORDER BY lastUpdated ASC")
    fun getAllNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewNote(note: Notes)

    @Delete
    suspend fun deleteNote(note: Notes)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOldNote(note: Notes)

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM notes_table WHERE title LIKE :searchQuery OR body Like :searchQuery")
    fun searchNote(searchQuery: String): LiveData<List<Notes>>
}