package com.geekbrains.kotlin.viewmodel

import androidx.lifecycle.Observer
import com.geekbrains.kotlin.data.NotesRepository2
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.data.model.NoteResult
import com.geekbrains.kotlin.ui.base.BaseViewModel
import com.geekbrains.kotlin.ui.note.NoteViewState

class NoteViewModel(private val noteRep:NotesRepository2) : BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val pendingNote: Note?
        get() = viewStateLiveData.value?.data?.note


    fun save(inNote: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = inNote))
    }

    fun deleteNote(){
        pendingNote?.let {
            noteRep.deleteNote(it.id).observeForever(object : Observer<NoteResult> {
                override fun onChanged(t: NoteResult?) {
                    t ?: return
                    when (t) {
                        is NoteResult.Success<*> -> {
                            viewStateLiveData.value = NoteViewState(NoteViewState.Data(isDel = true))
                        }
                        is NoteResult.Error -> {
                            viewStateLiveData.value = NoteViewState(error = t.error)
                        }
                    }
                }
            })
        }
    }

    fun loadNote(noteId: String) {
        noteRep.getNoteById(noteId).observeForever(object : Observer<NoteResult> {
            override fun onChanged(t: NoteResult?) {
                t ?: return
                when (t) {
                    is NoteResult.Success<*> -> {
                        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = t.data as Note))
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
            noteRep.saveNote(it)
        }
    }
}