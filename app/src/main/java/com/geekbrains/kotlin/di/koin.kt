package com.geekbrains.kotlin.di

import com.geekbrains.kotlin.data.NotesRepository2
import com.geekbrains.kotlin.data.provider.FireStoreProvider
import com.geekbrains.kotlin.data.provider.RemoteDataProvider
import com.geekbrains.kotlin.viewmodel.KeepViewModel
import com.geekbrains.kotlin.viewmodel.NoteViewModel
import com.geekbrains.kotlin.viewmodel.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single<RemoteDataProvider> { FireStoreProvider(get(),get()) }
    single { NotesRepository2(get()) }
}

val splashModule= module {
    viewModel { SplashViewModel(get()) }
}
val noteModule = module {
    viewModel { NoteViewModel(get()) }
}

val keepModule = module {
    viewModel { KeepViewModel(get()) }
}
