package com.geekbrains.kotlin.data.provider

import androidx.lifecycle.MutableLiveData
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.data.entity.AuthUser
import com.geekbrains.kotlin.data.error.FailAuthException

import com.geekbrains.kotlin.data.model.NoteResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*

class FireStoreProvider(private val fbAuth: FirebaseAuth,private  val store: FirebaseFirestore) : RemoteDataProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val currentUser get() =  fbAuth.currentUser

    override fun getCurrentUser() = MutableLiveData<AuthUser?>().apply {
        value = currentUser?.let {
            AuthUser(it.displayName!!,it.email!!)
        }
    }

    private val userNodesCollection: CollectionReference
    get() = currentUser?.let {
        store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION) }
            ?: throw FailAuthException()

    override fun subscribeToAllNotes() = MutableLiveData<NoteResult>().apply {
        try {
            userNodesCollection.addSnapshotListener { snapshot, e ->
                e?.let {
                    throw it
                } ?: let {
                    snapshot?.let { snapshot ->
                        this.value = NoteResult.Success(snapshot.documents.map { it.toObject(Note::class.java) })
                    }
                }
            }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun getNoteById(id: String) = MutableLiveData<NoteResult>().apply{
        try {
            userNodesCollection.document(id).get()
                    .addOnSuccessListener { snapshot ->
                        this.value = NoteResult.Success(snapshot.toObject(Note::class.java))
                    }.addOnFailureListener {
                        throw it
                    }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun saveNote(note: Note) = MutableLiveData<NoteResult>().apply {
        try {
            userNodesCollection.document(note.id).set(note)
                    .addOnSuccessListener {
                        this.value = NoteResult.Success(note)
                    }.addOnFailureListener {
                        throw it
                    }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }

    override fun deleteNote(noteId:String) = MutableLiveData<NoteResult>().apply {
        try {
            userNodesCollection.document(noteId).delete()
                    .addOnSuccessListener {
                        this.value = NoteResult.Success(null)
                    }.addOnFailureListener {
                        throw it
                    }
        } catch (e: Throwable) {
            value = NoteResult.Error(e)
        }
    }
}