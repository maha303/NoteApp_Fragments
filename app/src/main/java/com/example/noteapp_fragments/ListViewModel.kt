package com.example.noteapp_fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(application: Application):AndroidViewModel(application) {
    private val note:MutableLiveData<List<Note>> = MutableLiveData()
    private var db:FirebaseFirestore=Firebase.firestore

    fun getNote():LiveData<List<Note>>{
        return note
    }
    fun getData(){
        db.collection("notes")
            .get()
            .addOnSuccessListener { result->
                val temp= arrayListOf<Note>()
                for (document in result){
                    document.data.map { (key,value)->temp.add(Note(document.id,value.toString())) }
                }
                note.postValue(temp)
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }
    }
    fun addNote(note: Note){
        CoroutineScope(Dispatchers.IO).launch {
            val newNote = hashMapOf(
                "noteText" to note.noteText,
            )
            db.collection("notes").add(newNote)
            getData()
        }
        }
    fun deleteNote(noteId: String){
        CoroutineScope(Dispatchers.IO).launch{
            db.collection("notes")
                .get()
                .addOnSuccessListener {
                    resulte->
                    for (document in resulte){
                        if(document.id==noteId){
                            db.collection("notes").document(noteId).delete()
                        }
                    }
                    getData()
                }
                .addOnFailureListener {
                        exception ->
                    Log.w("MainActivity", "Error getting documents.", exception)
                }
        }
    }

    }
