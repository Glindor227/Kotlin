package com.geekbrains.kotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.geekbrains.kotlin.data.NotesRepository
import com.geekbrains.kotlin.ui.main.KeepViewState


class KeepViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<KeepViewState> = MutableLiveData()

    init {
        NotesRepository.getNotes().observeForever {
            viewStateLiveData.value = viewStateLiveData.value?.copy(notes = it) ?: KeepViewState(it)
        }

    }

    fun viewState(): LiveData<KeepViewState> = viewStateLiveData


}