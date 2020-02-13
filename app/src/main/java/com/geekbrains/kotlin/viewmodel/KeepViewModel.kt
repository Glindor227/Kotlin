package com.geekbrains.kotlin.viewmodel

import androidx.lifecycle.Observer
import com.geekbrains.kotlin.data.NotesRepository2
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.data.model.NoteResult
import com.geekbrains.kotlin.ui.base.BaseViewModel
import com.geekbrains.kotlin.ui.main.KeepViewState


class KeepViewModel : BaseViewModel<List<Note>?, KeepViewState>() {

    private val notesObserver = object : Observer<NoteResult> {
        override fun onChanged(t: NoteResult?) {
            t ?: return

            when(t){
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = KeepViewState(notes = t.data as? List<Note>)
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = KeepViewState(error = t.error)
                }
            }
        }
    }

    private val repositoryNotes = NotesRepository2.getNotes()

    init {
        viewStateLiveData.value = KeepViewState()
        repositoryNotes.observeForever(notesObserver)
    }

//    fun viewState(): LiveData<KeepViewState> = viewStateLiveData

    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
        super.onCleared()
    }

}