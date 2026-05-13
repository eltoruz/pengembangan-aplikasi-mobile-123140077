package com.eltoruz.myprofileapp.di

import com.eltoruz.myprofileapp.db.DatabaseDriverFactory
import com.eltoruz.myprofileapp.platform.BatteryInfo
import com.eltoruz.myprofileapp.platform.NetworkMonitor
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single { DatabaseDriverFactory(get()) }
    single {
        NetworkMonitor().apply { init(get()) }
    }
    single {
        BatteryInfo().apply { init(get()) }
    }
}
