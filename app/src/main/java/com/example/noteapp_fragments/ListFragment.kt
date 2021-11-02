package com.example.noteapp_fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ListFragment : Fragment() {
    private lateinit var rvMain: RecyclerView
    private lateinit var rvAdapter: NoteAdapter
    private lateinit var edNote: EditText
    private lateinit var btnSubmit: Button

    lateinit var listViewModel: ListViewModel
    private lateinit var notes: List<Note>
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        notes = arrayListOf()

        listViewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listViewModel.getNote().observe(viewLifecycleOwner, { notes ->
            rvAdapter.update(notes)
        })
        edNote=view.findViewById(R.id.tvNewNote)
        btnSubmit=view.findViewById(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            listViewModel.addNote(Note("",edNote.text.toString()))
            edNote.text.clear()
            edNote.clearFocus()
        }
        rvMain=view.findViewById(R.id.rvMain)
        rvAdapter= NoteAdapter(this)
        rvMain.adapter=rvAdapter
        rvMain.layoutManager=LinearLayoutManager(requireContext())
        listViewModel.getData()
        return view
    }
    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            listViewModel.getData()
        }
    }
}