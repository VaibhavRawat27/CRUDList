package com.vaibhavrawat.crudlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    @ColumnInfo
    var title : String ?= null,
    @ColumnInfo
    var description : String ?= null,
)