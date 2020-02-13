package com.geekbrains.kotlin.ui.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.geekbrains.kotlin.R
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.ui.KeepRVAdapter
import com.geekbrains.kotlin.ui.base.BaseActivity
import com.geekbrains.kotlin.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*

import com.geekbrains.kotlin.viewmodel.KeepViewModel
import kotlinx.android.synthetic.main.activity_note.*

class MainActivity : BaseActivity<List<Note>?, KeepViewState>() {

    override val viewModel: KeepViewModel by lazy {
        ViewModelProvider(this).get(KeepViewModel::class.java)
    }

    override val layoutRes = R.layout.activity_main
    private lateinit var adapter: KeepRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rv_notes.layoutManager =  StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = KeepRVAdapter { note ->
            NoteActivity.start(this, note.id)
        }

        rv_notes.adapter = adapter
        fab.setOnClickListener {
            NoteActivity.start(this)
        }
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

}
