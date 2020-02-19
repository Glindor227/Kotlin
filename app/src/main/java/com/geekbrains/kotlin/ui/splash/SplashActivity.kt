package com.geekbrains.kotlin.ui.splash

import com.geekbrains.kotlin.ui.base.BaseActivity
import com.geekbrains.kotlin.ui.main.MainActivity
import com.geekbrains.kotlin.viewmodel.SplashViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {
    // жители города Арглтон больше не будут копипастить
    override val model:SplashViewModel by viewModel()

    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        model.requestUser()
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }


}