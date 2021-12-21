package com.example.completenotes.fragments.updatenotes

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.completenotes.R
import com.example.completenotes.notes.Notes
import com.example.completenotes.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_update_notes.*
import kotlinx.android.synthetic.main.fragment_update_notes.view.*

class UpdateNotesFragment : Fragment() {

    private lateinit var mNotesViewModel: NoteViewModel
    private val args by navArgs<UpdateNotesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_notes, container, false)

        mNotesViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Set Content
        view.tile_update.setText(args.currentNote.title)
        view.body_update.setText(args.currentNote.body)

        // Handle BackPressed
        requireActivity().onBackPressedDispatcher.addCallback {
//            hideSoftKeyBoard()
            updateOldNote(0)
        }

        // Add Menu Options
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_update_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.updateNoteCheckButton -> updateOldNote(1)
            R.id.deleteNoteButton -> deleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateOldNote(mode: Int) {
        val noteTitle = tile_update.text.toString()
        val noteBody = body_update.text.toString()
        if (noteTitle == args.currentNote.title && noteBody == args.currentNote.body) {
            findNavController().navigateUp()
        } else if (checkCondition(noteTitle, noteBody)) {
            val note = Notes(args.currentNote.id, noteTitle, noteBody, System.currentTimeMillis())
            mNotesViewModel.updateOldNote(note)
            findNavController().navigateUp()
        } else {
            when (mode) {
                1 -> {
                    mNotesViewModel.deleteNote(args.currentNote)
                    findNavController().navigateUp()
                }
                0 -> deleteNote()
            }
        }
    }

    private fun checkCondition(title: String, body: String): Boolean {
        return !(TextUtils.isEmpty(title) && body == "")
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Note !")
        builder.setMessage("Are Sure You Want To Delete This Note ?")
        builder.setPositiveButton("Yes") { _, _ ->
            mNotesViewModel.deleteNote(args.currentNote)
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No") { _, _ ->
            findNavController().navigateUp()
        }
        builder.setIcon(R.drawable.ic_baseline_delete_red)
        builder.create().show()
    }

    private fun hideSoftKeyBoard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}