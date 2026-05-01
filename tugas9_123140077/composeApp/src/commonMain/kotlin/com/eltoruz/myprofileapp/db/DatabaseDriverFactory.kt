package com.eltoruz.myprofileapp.db

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

object DatabaseHelper {
    private var database: NotesDatabase? = null

    fun getDatabase(driverFactory: DatabaseDriverFactory): NotesDatabase {
        if (database == null) {
            val driver = driverFactory.createDriver()
            database = NotesDatabase(driver)
        }
        return database!!
    }
}
