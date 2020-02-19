package com.geekbrains.kotlin

import android.app.Application
import com.geekbrains.kotlin.di.appModule
import com.geekbrains.kotlin.di.keepModule
import com.geekbrains.kotlin.di.noteModule
import com.geekbrains.kotlin.di.splashModule
import org.koin.android.ext.android.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, splashModule, keepModule, noteModule))
    }
}