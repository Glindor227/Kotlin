package com.geekbrains.kotlin.viewmodel

import com.geekbrains.kotlin.data.NotesRepository2
import com.geekbrains.kotlin.data.error.FailAuthException
import com.geekbrains.kotlin.ui.base.BaseViewModel
import com.geekbrains.kotlin.ui.splash.SplashViewState

class SplashViewModel(private val noteRep:NotesRepository2):BaseViewModel<Boolean?,SplashViewState>(){
    fun requestUser(){
        noteRep.getCurrentUser().observeForever {
            viewStateLiveData.value = it?.let { SplashViewState(auth = true) }
            ?: SplashViewState(error = FailAuthException())
        }
    }
}