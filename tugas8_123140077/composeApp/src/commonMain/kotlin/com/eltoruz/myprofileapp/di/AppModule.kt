package com.eltoruz.myprofileapp.di

import com.eltoruz.myprofileapp.data.NoteRepository
import com.eltoruz.myprofileapp.data.SettingsManager
import com.eltoruz.myprofileapp.db.DatabaseHelper
import com.eltoruz.myprofileapp.platform.DeviceInfo
import com.eltoruz.myprofileapp.viewmodel.NoteViewModel
import com.eltoruz.myprofileapp.viewmodel.ProfileViewModel
import com.eltoruz.myprofileapp.viewmodel.SettingsViewModel
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformModule: Module

val commonModule = module {
    single { Settings() }
    single { DatabaseHelper.getDatabase(get()) }
    single { NoteRepository(get()) }
    single { SettingsManager(get()) }
    single { DeviceInfo() }

    single { NoteViewModel(get(), get()) }
    single { SettingsViewModel(get()) }
    single { ProfileViewModel() }
}

val appModules = listOf(commonModule, platformModule)
