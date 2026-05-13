package com.eltoruz.myprofileapp.di

import com.eltoruz.myprofileapp.data.NoteRepository
import com.eltoruz.myprofileapp.data.SettingsManager
import com.eltoruz.myprofileapp.viewmodel.NoteViewModel
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

class KoinModuleTest : KoinTest {

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                module {
                    single<NoteRepository> { mockk(relaxed = true) }
                    single<SettingsManager> { mockk(relaxed = true) }
                    single { NoteViewModel(get(), get()) }
                }
            )
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun noteViewModelIsInjectedCorrectly() {
        val viewModel: NoteViewModel by inject()
        assertNotNull(viewModel)
    }

    @Test
    fun noteRepositoryIsInjectedCorrectly() {
        val repository: NoteRepository by inject()
        assertNotNull(repository)
    }

    @Test
    fun settingsManagerIsInjectedCorrectly() {
        val settingsManager: SettingsManager by inject()
        assertNotNull(settingsManager)
    }
}
