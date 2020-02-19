package com.geekbrains.kotlin.data.provider

import androidx.lifecycle.MutableLiveData
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.data.entity.AuthUser
import com.geekbrains.kotlin.data.error.FailAuthException

import com.geekbrains.kotlin.data.model.NoteResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot

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

//    private val noteReference = store.collection(NOTES_COLLECTION)
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
                        val notes = mutableListOf<Note>()
                        for (doc: QueryDocumentSnapshot in snapshot) {
                            notes.add(doc.toObject(Note::class.java))
                        }
                        this.value = NoteResult.Success(notes)
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