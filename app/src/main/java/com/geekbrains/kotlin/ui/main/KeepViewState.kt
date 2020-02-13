package com.geekbrains.kotlin.ui.main

import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.ui.base.BaseViewState

class KeepViewState(val notes: List<Note>? = null, error: Throwable? = null):
        BaseViewState<List<Note>?>(notes, error)