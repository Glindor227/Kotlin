package com.geekbrains.kotlin.data.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.data.error.FailAuthException
import com.geekbrains.kotlin.data.model.NoteResult
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import org.junit.*
import org.junit.Assert.assertEquals


class FireStoreProviderTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockkDB = mockk<FirebaseFirestore>()
    private val mockkAuth = mockk<FirebaseAuth>()

    private val mockkProvider = FireStoreProvider(mockkAuth,mockkDB)

    private val mockkResultColl = mockk<CollectionReference>()
    private val mockkUser = mockk<FirebaseUser>()

    private val mockkNode = listOf(Note("0"),Note("3"),Note("4"),Note("40"))

    private val mockkDocSH0 = mockk<DocumentSnapshot>()
    private val mockkDocSH3 = mockk<DocumentSnapshot>()
    private val mockkDocSH4 = mockk<DocumentSnapshot>()
    private val mockkDocSH40 = mockk<DocumentSnapshot>()

    companion object {
        @BeforeClass
        fun beforeClass(){
        }
        @AfterClass
        fun afterClass(){
        }
    }

    @Before
    fun before(){
        clearAllMocks()
        every { mockkAuth.currentUser } returns mockkUser
        every { mockkUser.uid } returns ""
        every { mockkDB.collection(any()).document(any()).collection(any()) } returns mockkResultColl
        every { mockkDocSH0.toObject(Note::class.java) } returns mockkNode[0]
        every { mockkDocSH3.toObject(Note::class.java) } returns mockkNode[1]
        every { mockkDocSH4.toObject(Note::class.java) } returns mockkNode[2]
        every { mockkDocSH40.toObject(Note::class.java) } returns mockkNode[3]
    }


    @After
    fun after(){
    }


    @Test
    fun `subscribeToAllNotes() user null`() {
        var result: Any? =null
        every {  mockkAuth.currentUser } returns null
        mockkProvider.subscribeToAllNotes().observeForever{
            result = (it as NoteResult.Error).error
        }
        assert(result is FailAuthException)
    }

    @Test
    fun `subscribeToAllNotes() return Notes`() {
        var result: List<Note>? =null
        val mockkSnapshot = mockk<QuerySnapshot>()
        val mySlot = slot<EventListener<QuerySnapshot>>()
        every { mockkSnapshot.documents  } returns listOf(mockkDocSH0,mockkDocSH3,mockkDocSH4,mockkDocSH40)
        every { mockkResultColl.addSnapshotListener(capture(mySlot)) } returns mockk()
        mockkProvider.subscribeToAllNotes().observeForever {
            result = (it as NoteResult.Success<List<Note>>)?.data
        }
        mySlot.captured.onEvent(mockkSnapshot,null)
        Assert.assertEquals(mockkNode,result)
    }

    @Test
    fun `getNoteById() get Note`() {
        var result: Note? = null
        val slot = slot<OnSuccessListener<DocumentSnapshot>>()
        val mockkSnapshot = mockk<DocumentSnapshot>()

        every { mockkSnapshot.toObject(Note::class.java)} returns mockkNode[0]
        every { mockkResultColl.document(mockkNode[0].id).get()
                .addOnSuccessListener(capture(slot))} returns mockk()

        mockkProvider.getNoteById(mockkNode[0].id).observeForever {
            result = (it as? NoteResult.Success<Note>)?.data
        }
        slot.captured.onSuccess(mockkSnapshot)

        assertEquals(mockkNode[0], result)

    }


    @Test
    fun `saveNote() call set`() {
        val mockkDocumentReference = mockk<DocumentReference>()
        every { mockkResultColl.document(mockkNode[0].id) } returns mockkDocumentReference
        mockkProvider.saveNote(mockkNode[0])
        verify(exactly = 1){mockkDocumentReference.set(mockkNode[0])}
    }

    @Test
    fun `getNoteById() call get`() {
        val mockkDocumentReference = mockk<DocumentReference>()
        every { mockkResultColl.document(mockkNode[0].id) } returns mockkDocumentReference
        mockkProvider.getNoteById(mockkNode[0].id)
        verify(exactly = 1){mockkDocumentReference.get()}
    }


    @Test
    fun `deleteNote() call delete `() {
        val mockkDocumentReference = mockk<DocumentReference>()
        every { mockkResultColl.document(mockkNode[0].id) } returns mockkDocumentReference
        mockkProvider.deleteNote(mockkNode[0].id)
        verify(exactly = 1){mockkDocumentReference.delete()}
    }
}