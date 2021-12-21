package com.example.completenotes.notes

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "notes_table")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String?,
    val body: String,
    val lastUpdated: Long = 0,
//    val priority: Int,
//    val date: String
) : Parcelable
