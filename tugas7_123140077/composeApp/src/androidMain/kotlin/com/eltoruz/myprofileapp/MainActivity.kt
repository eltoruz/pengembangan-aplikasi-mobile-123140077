package com.eltoruz.myprofileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eltoruz.myprofileapp.db.DatabaseDriverFactory
import com.russhwolf.settings.Settings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val driverFactory = DatabaseDriverFactory(this)
        val settings = Settings()

        setContent {
            App(driverFactory = driverFactory, settings = settings)
        }
    }
}