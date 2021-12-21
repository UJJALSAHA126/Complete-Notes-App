package com.example.completenotes.fragments.addnotes

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.completenotes.R
import com.example.completenotes.notes.Notes
import com.example.completenotes.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_add_notes.*

class AddNotesFragment : Fragment() {

    private lateinit var mNotesViewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_notes, container, false)

        // Inti ViewModel
        mNotesViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        // Handle Back Button
        requireActivity().onBackPressedDispatcher.addCallback{
            insertNote(0)
        }

        // Add Menu Options
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_add_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addNoteCheckButton) {
            insertNote(1)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertNote(mode: Int) {
//        hideSoftKeyBoard()
        val noteTitle = tile_add.text.toString()
        val noteBody = body_add.text.toString()

        if (checkCondition(noteTitle, noteBody)) {
            val note = Notes(0, noteTitle, noteBody,System.currentTimeMillis())
            mNotesViewModel.insertNote(note)
        }
        else if (mode == 1) {
            Toast.makeText(requireContext(), "Please Write A Note", Toast.LENGTH_SHORT).show()
            return
        }
        findNavController().navigateUp()
    }

    private fun checkCondition(title: String, body: String): Boolean {
        return !(TextUtils.isEmpty(title) && body == "")
    }
    private fun hideSoftKeyBoard(){
//        Toast.makeText(requireContext(), "Goru", Toast.LENGTH_SHORT).show()
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}