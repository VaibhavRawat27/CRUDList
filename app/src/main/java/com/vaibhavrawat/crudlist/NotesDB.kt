package com.vaibhavrawat.crudlist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(version = 1, entities = [NotesEntity::class])
abstract class NotesDB : RoomDatabase() {
    abstract fun notesDao() : NotesInterface
    companion object{
        var notesDB : NotesDB ?= null
        fun getNotesDatabase(context: Context) : NotesDB {
            if (notesDB == null) {
                notesDB = Room.databaseBuilder(context,NotesDB::class.java,context.resources.getString(R.string.app_name)).build()
            }
            return notesDB!!
        }
    }
}