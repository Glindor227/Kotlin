package com.geekbrains.kotlin.data

import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.data.provider.FireStoreProvider
import com.geekbrains.kotlin.data.provider.RemoteDataProvider

object NotesRepository2 {

    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getNotes() = remoteProvider.subscribeToAllNotes()
    fun saveNote(note: Note) = remoteProvider.saveNote(note)
    fun getNoteById(id: String) = remoteProvider.getNoteById(id)

    fun getCurrentUser() = remoteProvider.getCurrentUser()
}