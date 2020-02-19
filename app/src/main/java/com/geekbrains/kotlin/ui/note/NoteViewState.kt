package com.geekbrains.kotlin.ui.note

import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.ui.base.BaseViewState

class NoteViewState(data: Data = Data(), error: Throwable? = null) :
        BaseViewState<NoteViewState.Data>(data, error){
    data class Data(val isDel:Boolean =false,val note: Note? = null)
}