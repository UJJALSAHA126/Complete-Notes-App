package com.example.completenotes.fragments.homenotes.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.completenotes.R
import com.example.completenotes.diffUtil.MyDiffUtilNotes
import com.example.completenotes.fragments.homenotes.HomeNotesFragmentDirections
import com.example.completenotes.fragments.homenotes.ItemClickedListener
import com.example.completenotes.notes.Notes

class HomeNotesAdapter(private val context: Context, private val listener: ItemClickedListener) :
    RecyclerView.Adapter<HomeNotesAdapter.MyViewHolder>() {

    private val notesList = ArrayList<Notes>()
    private lateinit var animation: Animation

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tileNote: TextView = itemView.findViewById(R.id.note_title)
        val body: TextView = itemView.findViewById(R.id.note_body)
        val deleteButton: ImageButton = itemView.findViewById(R.id.delete_note)
        val noteLayout: ConstraintLayout = itemView.findViewById(R.id.note_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_style, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        animation = AnimationUtils.loadAnimation(context, R.anim.slide_in)
        holder.noteLayout.animation = animation
        val note = notesList[notesList.size - 1 - position]
        note.title?.let {
            holder.tileNote.text = it
        }
        holder.body.text = note.body

        holder.deleteButton.setOnClickListener {
            listener.onItemClicked(note)
        }
        holder.noteLayout.setOnClickListener {
            val action = HomeNotesFragmentDirections.actionHomeNotesToUpdateNotes(note)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setNotes(notes: List<Notes>) {
        Log.d("SIZE_NOTE", notesList.size.toString() + " " + notes.size.toString())
        val diffUtil = MyDiffUtilNotes(notesList, notes)

        notesList.clear()
        notesList.addAll(notes)

        val diffResult = DiffUtil.calculateDiff(diffUtil)
        diffResult.dispatchUpdatesTo(this)

        Log.d("SIZE_NOTE -> ", notesList.size.toString() + " " + notes.size.toString())
//        notifyDataSetChanged()
    }
}