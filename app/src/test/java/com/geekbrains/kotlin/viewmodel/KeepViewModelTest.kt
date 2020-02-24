package com.geekbrains.kotlin.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.geekbrains.kotlin.data.NotesRepository2
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.data.model.NoteResult
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.*

class KeepViewModelTest {
    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    private val mockkRepository = mockk<NotesRepository2>()
    private val notesLiveData = MutableLiveData<NoteResult>()
    private lateinit var viewModel: KeepViewModel



    @Before
    fun before(){
        clearAllMocks()
        every { mockkRepository.getNotes() } returns notesLiveData
        viewModel = KeepViewModel(mockkRepository)

    }


    @After
    fun after(){
    }

    @Test
    fun `call getNotes`() {
        verify(exactly = 1) { mockkRepository.getNotes() }
    }

    @Test
    fun `return notes`() {
        var result:List<Note>? = null
        val mockkNode = listOf(Note("0"),Note("3"),Note("4"),Note("40"))
        viewModel.getViewState().observeForever {
            result = it.data
        }
        notesLiveData.value = NoteResult.Success(mockkNode)
        Assert.assertEquals(result,mockkNode)
    }

    @Test
    fun `return error`(){
        var result:Throwable? = null
        val mockkNode = Throwable("опс!")
        viewModel.getViewState().observeForever {
            result = it.error
        }
        notesLiveData.value = NoteResult.Error(error = mockkNode)
        Assert.assertEquals(result,mockkNode)
    }

    @Test
    fun `remove observer`() {
        viewModel.onCleared()
        assert(!notesLiveData.hasObservers())
    }

}