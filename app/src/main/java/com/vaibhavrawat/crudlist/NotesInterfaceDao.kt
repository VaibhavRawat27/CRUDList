package com.vaibhavrawat.crudlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesInterface {
    @Insert
    fun insertNoteData(notesEntity: NotesEntity)

    @Query("SELECT * FROM NotesEntity")
    fun getNotes(): List<NotesEntity>

    @Delete
    fun deleteNotes(notesEntity: NotesEntity)

    @Update
    fun updateNotes(notesEntity: NotesEntity)
}