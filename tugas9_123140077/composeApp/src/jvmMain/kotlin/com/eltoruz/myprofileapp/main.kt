package com.eltoruz.myprofileapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.eltoruz.myprofileapp.di.appModules
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(appModules)
    }
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "MyProfileApp",
        ) {
            App()
        }
    }
}