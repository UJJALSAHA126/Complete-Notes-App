package com.example.completenotes.fragments.homenotes

import com.example.completenotes.notes.Notes

interface ItemClickedListener {
    fun onItemClicked(note: Notes)
}