package com.geekbrains.kotlin.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.geekbrains.kotlin.data.NotesRepository2
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.data.model.NoteResult
import com.geekbrains.kotlin.ui.note.NoteViewState
import io.mockk.*
import org.junit.*


class NoteViewModelTest {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockkRepository = mockk<NotesRepository2>()
    private val noteLiveData = MutableLiveData<NoteResult>()

    private val testNote = Note("1","title","text")
    private lateinit var viewModel: NoteViewModel


    @Before
    fun before() {
        clearAllMocks()
        every { mockkRepository.getNoteById(testNote.id) } returns noteLiveData
        every { mockkRepository.deleteNote(testNote.id) } returns noteLiveData
        every { mockkRepository.saveNote(testNote) } returns  noteLiveData
        viewModel = NoteViewModel(mockkRepository)
    }

    @After
    fun after(){
    }

    @Test
    fun `loadNote() return NoteViewState`() {
        var result: NoteViewState.Data? = null
        val testData = NoteViewState.Data(false, testNote)
        viewModel.getViewState().observeForever {
            result = it.data
        }
        viewModel.loadNote(testNote.id)
        noteLiveData.value = NoteResult.Success(testNote)
        Assert.assertEquals(testData, result)
    }

    @Test
    fun `loadNote() return error`() {
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.getViewState().observeForever {
            result = it.error
        }
        viewModel.loadNote(testNote.id)
        noteLiveData.value = NoteResult.Error(error = testData)
        Assert.assertEquals(testData, result)
    }

    @Test
    fun `deleteNote() return isDeleted`() {
        var result: NoteViewState.Data? = null
        val testData = NoteViewState.Data(true, null)
        viewModel.getViewState().observeForever {
            result = it.data
        }
        viewModel.save(testNote)
        viewModel.deleteNote()
        noteLiveData.value = NoteResult.Success(null)
        Assert.assertEquals(testData, result)
    }

    @Test
    fun `deleteNote() return error`() {
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.getViewState().observeForever {
            result = it.error
        }
        viewModel.save(testNote)
        viewModel.deleteNote()
        noteLiveData.value = NoteResult.Error(error = testData)
        Assert.assertEquals(testData, result)
    }

    @Test
    fun `save changes`() {
        viewModel.save(testNote)
        viewModel.onCleared()
        verify { mockkRepository.saveNote(testNote) }
    }

}