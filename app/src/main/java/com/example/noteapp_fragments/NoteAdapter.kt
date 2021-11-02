package com.example.noteapp_fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp_fragments.databinding.ItemRowBinding

class NoteAdapter(private val listFragment: ListFragment):RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {
    private var notes= emptyList<Note>()
    class ItemViewHolder(val binding: ItemRowBinding):RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note=notes[position]
        holder.binding.apply {
            tvNote.text=note.noteText
            ivEdit.setOnClickListener {
                with(listFragment.sharedPreferences.edit()){
                    putString("noteId",note.id)
                    apply()
                }
                listFragment.findNavController().navigate(R.id.action_listFragment_to_update)
            }
            ivDelete.setOnClickListener {
                listFragment.listViewModel.deleteNote(note.id)
            }
        }
    }

    override fun getItemCount()=notes.size
    fun update(notes : List<Note>){
        this.notes=notes
        notifyDataSetChanged()
    }
}