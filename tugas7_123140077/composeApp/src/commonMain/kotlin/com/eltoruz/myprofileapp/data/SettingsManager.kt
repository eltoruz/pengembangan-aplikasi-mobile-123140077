package com.eltoruz.myprofileapp.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set

class SettingsManager(private val settings: Settings) {

    companion object {
        private const val KEY_THEME = "app_theme"
        private const val KEY_SORT_ORDER = "sort_order"
    }

    // Theme: "light", "dark", "system"
    var theme: String
        get() = settings[KEY_THEME, "system"]
        set(value) { settings[KEY_THEME] = value }

    // Sort order: "updated_at", "title", "created_at"
    var sortOrder: String
        get() = settings[KEY_SORT_ORDER, "updated_at"]
        set(value) { settings[KEY_SORT_ORDER] = value }
}
