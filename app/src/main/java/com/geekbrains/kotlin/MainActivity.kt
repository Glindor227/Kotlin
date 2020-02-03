package com.geekbrains.kotlin

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.arch.lifecycle.ViewModelProviders

import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.geekbrains.kotlin.viewmodel.KeepViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: KeepViewModel
    lateinit var adapter: NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)

        viewModel = ViewModelProviders.of(this).get(KeepViewModel::class.java)

        rv_notes.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        adapter = NotesRVAdapter()
        rv_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer {
            it?.let {
                adapter.notes = it.notes
            }
        })

    }

}
