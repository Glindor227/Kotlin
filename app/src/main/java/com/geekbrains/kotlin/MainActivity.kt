package com.geekbrains.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUserView()

    }

    private fun initUserView() {
        buttonTest.setOnClickListener { Toast.makeText(this,text.text,Toast.LENGTH_SHORT).show()}
    }
}
