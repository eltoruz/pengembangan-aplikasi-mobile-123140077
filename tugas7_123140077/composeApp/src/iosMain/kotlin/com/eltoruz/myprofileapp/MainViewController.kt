package com.eltoruz.myprofileapp

import androidx.compose.ui.window.ComposeUIViewController
import com.eltoruz.myprofileapp.db.DatabaseDriverFactory
import com.russhwolf.settings.Settings

fun MainViewController() = ComposeUIViewController {
    App(
        driverFactory = DatabaseDriverFactory(),
        settings = Settings()
    )
}