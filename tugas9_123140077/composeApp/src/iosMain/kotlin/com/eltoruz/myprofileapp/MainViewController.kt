package com.eltoruz.myprofileapp

import androidx.compose.ui.window.ComposeUIViewController
import com.eltoruz.myprofileapp.di.appModules
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModules)
    }
}

fun MainViewController() = ComposeUIViewController {
    App()
}