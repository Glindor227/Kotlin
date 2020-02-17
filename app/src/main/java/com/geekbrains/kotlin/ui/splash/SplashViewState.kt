package com.geekbrains.kotlin.ui.splash

import com.geekbrains.kotlin.ui.base.BaseViewState


class SplashViewState(auth:Boolean? = null, error: Throwable? = null):
        BaseViewState<Boolean?>(auth,error)