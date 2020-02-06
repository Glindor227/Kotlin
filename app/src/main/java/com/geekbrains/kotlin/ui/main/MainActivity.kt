package com.geekbrains.kotlin.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.geekbrains.kotlin.R
import com.geekbrains.kotlin.ui.KeepRVAdapter
import com.geekbrains.kotlin.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*

import com.geekbrains.kotlin.viewmodel.KeepViewModel

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: KeepViewModel
    lateinit var adapter: KeepRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)

        viewModel = ViewModelProvider(this).get(KeepViewModel::class.java)

        rv_notes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter = KeepRVAdapter{ note ->
            NoteActivity.start(this, note)
        }

        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer {
            it?.let {
                adapter.notes = it.notes
            }
        })

        fab.setOnClickListener {
           NoteActivity.start(this)
        }


    }

}
