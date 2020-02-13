package com.geekbrains.kotlin.ui.note

import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.ui.base.BaseViewState

class NoteViewState(note: Note? = null, error: Throwable? = null) :
        BaseViewState<Note?>(note, error)