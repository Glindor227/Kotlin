package com.geekbrains.kotlin.ui.splash

import androidx.lifecycle.ViewModelProvider
import com.geekbrains.kotlin.ui.base.BaseActivity
import com.geekbrains.kotlin.ui.main.MainActivity
import com.geekbrains.kotlin.viewmodel.SplashViewModel

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {
    // жители города Арглтон больше не будут копипастить
    override val viewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override val layoutRes: Int? = null

    override fun onResume() {
        super.onResume()
        viewModel.requestUser()
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