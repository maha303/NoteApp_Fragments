package com.example.noteapp_fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController


class Update : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_update, container, false)

         val sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        val updateViewModel = ViewModelProvider(this).get(UpdateViewModel::class.java)

        val edNoteUpdate = view.findViewById<EditText>(R.id.etNoteUpdate)
        val btNoteUpdate=view.findViewById<Button>(R.id.btNoteUpdate)
        btNoteUpdate.setOnClickListener {
            val noteId=sharedPreferences.getString("NoteId","").toString()

            updateViewModel.editNote(noteId,edNoteUpdate.text.toString())
            with(sharedPreferences.edit()) {
                putString("NoteId", edNoteUpdate.text.toString())
                apply()
            }
            findNavController().navigate(R.id.action_update_to_listFragment)
        }
        return view
    }
}