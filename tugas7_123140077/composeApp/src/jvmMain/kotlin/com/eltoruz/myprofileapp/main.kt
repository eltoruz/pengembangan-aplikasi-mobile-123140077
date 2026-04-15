package com.eltoruz.myprofileapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.eltoruz.myprofileapp.db.DatabaseDriverFactory
import com.russhwolf.settings.Settings

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MyProfileApp",
    ) {
        App(
            driverFactory = DatabaseDriverFactory(),
            settings = Settings()
        )
    }
}