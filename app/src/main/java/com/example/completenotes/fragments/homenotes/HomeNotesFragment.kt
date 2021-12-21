package com.example.completenotes.fragments.homenotes

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.completenotes.R
import com.example.completenotes.fragments.homenotes.adapter.HomeNotesAdapter
import com.example.completenotes.notes.Notes
import com.example.completenotes.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.delete_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_home_notes.view.*

class HomeNotesFragment : Fragment(), SearchView.OnQueryTextListener, ItemClickedListener {

    private lateinit var mNoteViewModel: NoteViewModel
    private lateinit var adapter: HomeNotesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_notes, container, false)

        // Inti Recycler View and Adapter
        recyclerView = view.recyclerViewNotes
        adapter = HomeNotesAdapter(requireContext(),this)

        // Init Note View Model
        mNoteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
        mNoteViewModel.allNotes.observe(viewLifecycleOwner, { notes ->
            adapter.setNotes(notes)
        })

        recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // Setting Adapter with Recycler View
        recyclerView.adapter = adapter

        // Handle Add Button Click
        val addButton = view.addButtonFAB
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeNotes_to_addNotes)
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            requireActivity().finish()
        }

        // Add Menu
        setHasOptionsMenu(true)
        return view
    }

    override fun onResume() {
        super.onResume()
        hideSoftKeyBoard()
//        Toast.makeText(requireContext(), "Goru !!", Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll_note) {
            deleteAllNotes()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_home_menu, menu)

        // Searching Users
        val search = menu.findItem(R.id.search_note)
        searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        onQueryTextChange(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            searchQuery(newText)
        }
        return true
    }

    private fun searchQuery(text: String) {
        val query = "%$text%"
        mNoteViewModel.searchNote(query).observe(viewLifecycleOwner, { notes ->
            notes?.let {
                adapter.setNotes(notes)
            }
        })
    }

    private fun deleteAllNotes() {
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setTitle("Delete Note !")
//        builder.setMessage("Are Sure You Want To Delete All Your Notes ?")
//        builder.setPositiveButton("Yes") { _, _ ->
//            mNoteViewModel.deleteAllNotes()
//        }
//        builder.setNegativeButton("No") { _, _ ->
//        }
//        builder.setIcon(R.drawable.ic_baseline_delete_red)
//        builder.create().show()

        val sheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
        val view = layoutInflater.inflate(R.layout.delete_bottom_sheet, bottomSheet, false)
        sheetDialog.setContentView(view)

        val yes: TextView = view.findViewById(R.id.yesButton)
        val no: TextView = view.findViewById(R.id.noButton)

        yes.setOnClickListener {
            mNoteViewModel.deleteAllNotes()
            sheetDialog.dismiss()
        }

        no.setOnClickListener{
            sheetDialog.dismiss()
        }

        sheetDialog.show()
    }

    private fun hideSoftKeyBoard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onItemClicked(note: Notes) {
//        val builder = AlertDialog.Builder(requireContext())
//        builder.setTitle("Delete ?")
//        builder.setMessage("Confirm Deleting This Note ?")
//        builder.setIcon(R.drawable.ic_baseline_delete_red)
//        builder.setPositiveButton("Yes") { _, _ ->
//            mNoteViewModel.deleteNote(note)
//        }
//        builder.setNegativeButton("No") { _, _ ->
//        }
//        builder.create().show()
        val sheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
        val view = layoutInflater.inflate(R.layout.delete_bottom_sheet, bottomSheet, false)
        sheetDialog.setContentView(view)

        val text : TextView = view.findViewById(R.id.textViewBottomDialog)
        val yes: TextView = view.findViewById(R.id.yesButton)
        val no: TextView = view.findViewById(R.id.noButton)
        "Do You Want\nTo Delete This Note ?".also { text.text = it }

        yes.setOnClickListener {
            mNoteViewModel.deleteNote(note)
            sheetDialog.dismiss()
        }

        no.setOnClickListener{
            sheetDialog.dismiss()
        }
        sheetDialog.show()
    }
}