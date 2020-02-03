package com.geekbrains.kotlin.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.geekbrains.kotlin.data.NotesRepository


class KeepViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<KeepViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = KeepViewState(NotesRepository.notes)
    }

    fun viewState(): LiveData<KeepViewState> = viewStateLiveData


}