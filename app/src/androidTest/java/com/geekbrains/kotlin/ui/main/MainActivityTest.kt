package com.geekbrains.kotlin.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import com.geekbrains.kotlin.R
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.ui.KeepRVAdapter
import com.geekbrains.kotlin.ui.note.NoteActivity
import com.geekbrains.kotlin.viewmodel.KeepViewModel
import io.mockk.every
import io.mockk.mockk
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Rule
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin

class MainActivityTest {

    @get:Rule
    val activityTestRule = IntentsTestRule(MainActivity::class.java,true,false)
    private val model: KeepViewModel = mockk(relaxed = true)
    private val viewStateLiveData = MutableLiveData<KeepViewState>()

    private val testNotes = listOf(
            Note("1", "title1", "text1"),
            Note("2", "title2", "text2"),
            Note("3", "title3", "text3")
    )

    @Before
    fun before() { loadKoinModules(listOf( module {
                            viewModel(override = true) { model }
                        }
                )
        )

        every { model.getViewState() } returns viewStateLiveData
        activityTestRule.launchActivity(null)
        viewStateLiveData.postValue(KeepViewState(notes = testNotes))
    }

    @After
    fun after(){
        stopKoin()
    }

    @Test
    fun check_data_is_displayed(){
        onView(withId(R.id.rv_notes)).perform(scrollToPosition<KeepRVAdapter.ViewHolder>(1))
        onView(withText(testNotes[1].text)).check(matches(isDisplayed()))
    }

    @Test
    fun check_start_note_activity(){
        onView(withId(R.id.rv_notes)).perform(actionOnItemAtPosition<KeepRVAdapter.ViewHolder>(1, click()))
        intended(allOf(
                hasComponent(NoteActivity::class.java.name),
                toPackage("com.geekbrains.kotlin.ui.note"),
                hasExtra(NoteActivity.EXTRA_NOTE, "2")))
    }
}