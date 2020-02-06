package com.geekbrains.kotlin.viewmodel

import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin.data.NotesRepository
import com.geekbrains.kotlin.data.entity.Note

class NoteViewModel: ViewModel() {
    private var pendingNote: Note? = null

    fun save(note: Note){
        pendingNote = note
    }

    override fun onCleared(){
        pendingNote?.let {
            NotesRepository.addNode(it)
        }
    }

}