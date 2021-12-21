package com.example.completenotes.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.completenotes.notes.Notes

class MyDiffUtilNotes(private val oldNotes: List<Notes>, private val newNotes: List<Notes>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldNotes.size
    }

    override fun getNewListSize(): Int {
        return newNotes.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldNotes[oldItemPosition].id == newNotes[newItemPosition].id)

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldNotes[oldItemPosition]
        val new = newNotes[newItemPosition]

        return when {
            old.id != new.id -> false

            old.body != new.body -> false

            old.title != new.title -> false

            old.lastUpdated != new.lastUpdated -> false

            else -> true
        }
    }
}