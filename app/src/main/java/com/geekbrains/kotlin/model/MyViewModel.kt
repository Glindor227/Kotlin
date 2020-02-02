package com.geekbrains.kotlin.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.geekbrains.kotlin.data.NotesRepository


class MyViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MyViewState> = MutableLiveData()

    init {
        viewStateLiveData.value = MyViewState(NotesRepository.getNotes())
    }

    fun viewState(): LiveData<MyViewState> = viewStateLiveData


}