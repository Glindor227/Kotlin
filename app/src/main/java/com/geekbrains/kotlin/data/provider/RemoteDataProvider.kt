package com.geekbrains.kotlin.data.provider

import androidx.lifecycle.LiveData
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.data.model.NoteResult

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
}