package com.geekbrains.kotlin.viewmodel

import androidx.lifecycle.Observer
import com.geekbrains.kotlin.data.NotesRepository2
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.data.model.NoteResult
import com.geekbrains.kotlin.ui.base.BaseViewModel
import com.geekbrains.kotlin.ui.note.NoteViewState

class NoteViewModel : BaseViewModel<Note?, NoteViewState>() {

    init {
        viewStateLiveData.value = NoteViewState()
    }

    private var pendingNote: Note? = null

    fun save(note: Note) {
        pendingNote = note
    }

    fun loadNote(noteId: String) {
        NotesRepository2.getNoteById(noteId).observeForever(object : Observer<NoteResult> {
            override fun onChanged(t: NoteResult?) {
                t ?: return
                when (t) {
                    is NoteResult.Success<*> -> {
                        viewStateLiveData.value = NoteViewState(note = t.data as Note)
                    }
                    is NoteResult.Error -> {
                        viewStateLiveData.value = NoteViewState(error = t.error)
                    }
                }
            }
        })
    }

    override fun onCleared() {
        pendingNote?.let {
            NotesRepository2.saveNote(it)
        }
    }
}