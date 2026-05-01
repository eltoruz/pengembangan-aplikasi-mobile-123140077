package com.eltoruz.myprofileapp.db.composeApp

import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.eltoruz.myprofileapp.db.NoteEntityQueries
import com.eltoruz.myprofileapp.db.NotesDatabase
import kotlin.Long
import kotlin.Unit
import kotlin.reflect.KClass

internal val KClass<NotesDatabase>.schema: SqlSchema<QueryResult.Value<Unit>>
  get() = NotesDatabaseImpl.Schema

internal fun KClass<NotesDatabase>.newInstance(driver: SqlDriver): NotesDatabase =
    NotesDatabaseImpl(driver)

private class NotesDatabaseImpl(
  driver: SqlDriver,
) : TransacterImpl(driver), NotesDatabase {
  override val noteEntityQueries: NoteEntityQueries = NoteEntityQueries(driver)

  public object Schema : SqlSchema<QueryResult.Value<Unit>> {
    override val version: Long
      get() = 1

    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
      driver.execute(null, """
          |CREATE TABLE NoteEntity (
          |    id INTEGER PRIMARY KEY AUTOINCREMENT,
          |    title TEXT NOT NULL,
          |    content TEXT NOT NULL,
          |    created_at INTEGER NOT NULL,
          |    updated_at INTEGER NOT NULL,
          |    is_favorite INTEGER NOT NULL DEFAULT 0
          |)
          """.trimMargin(), 0)
      return QueryResult.Unit
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Long,
      newVersion: Long,
      vararg callbacks: AfterVersion,
    ): QueryResult.Value<Unit> = QueryResult.Unit
  }
}
