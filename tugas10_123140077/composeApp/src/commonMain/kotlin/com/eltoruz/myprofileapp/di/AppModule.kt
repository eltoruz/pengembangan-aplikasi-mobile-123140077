package com.eltoruz.myprofileapp.di

import com.eltoruz.myprofileapp.data.AIRepository
import com.eltoruz.myprofileapp.data.AIRepositoryImpl
import com.eltoruz.myprofileapp.data.GeminiService
import com.eltoruz.myprofileapp.data.NoteRepository
import com.eltoruz.myprofileapp.data.SettingsManager
import com.eltoruz.myprofileapp.db.DatabaseHelper
import com.eltoruz.myprofileapp.platform.DeviceInfo
import com.eltoruz.myprofileapp.viewmodel.ChatViewModel
import com.eltoruz.myprofileapp.viewmodel.NoteViewModel
import com.eltoruz.myprofileapp.viewmodel.ProfileViewModel
import com.eltoruz.myprofileapp.viewmodel.SettingsViewModel
import com.russhwolf.settings.Settings
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val dataModule = module {
    single { Settings() }
    single { DatabaseHelper.getDatabase(get()) }
    single { NoteRepository(get()) }
    single { SettingsManager(get()) }
    single { DeviceInfo() }
}

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                })
            }
        }
    }
    single { GeminiService(get()) }
    single<AIRepository> { AIRepositoryImpl(get()) }
}

val viewModelModule = module {
    single { NoteViewModel(get(), get()) }
    single { SettingsViewModel(get()) }
    single { ProfileViewModel() }
    single { ChatViewModel(get()) }
}

val commonModule = module {
    includes(dataModule, networkModule, viewModelModule)
}

val appModules = listOf(dataModule, networkModule, viewModelModule, platformModule)
