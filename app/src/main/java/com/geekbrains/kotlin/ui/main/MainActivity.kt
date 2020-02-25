package com.geekbrains.kotlin.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.firebase.ui.auth.AuthUI

import com.geekbrains.kotlin.R
import com.geekbrains.kotlin.data.entity.Note
import com.geekbrains.kotlin.ui.KeepRVAdapter
import com.geekbrains.kotlin.ui.base.BaseActivity
import com.geekbrains.kotlin.ui.note.NoteActivity
import kotlinx.android.synthetic.main.activity_main.*

import com.geekbrains.kotlin.viewmodel.KeepViewModel
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<List<Note>?, KeepViewState>() {

    override val model: KeepViewModel by viewModel()

    companion object {
        fun start(cont:Context) = Intent(cont,MainActivity::class.java).apply { cont.startActivity(this) }
    }
    override val layoutRes = R.layout.activity_main
    private lateinit var adapter: KeepRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(my_toolbar)

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

    override fun onCreateOptionsMenu(menu: Menu?)= menuInflater.inflate(R.menu.menu2,menu).let { true }

    private fun showDialog(){
        alert {
            titleResource = R.string.logout_dialog_title
            messageResource = R.string.logout_dialog_message
            positiveButton(R.string.logout_dialog_ok){onLogout()}
            neutralPressed(R.string.logout_dialog_cancel){dialog ->  dialog.dismiss()}
        }.show()
    }
    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId){
        R.id.logout2 -> showDialog().let { true }
        else -> false
    }

    private fun onLogout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener{
                    startActivity(Intent(this,MainActivity::class.java))
                }
    }

}
