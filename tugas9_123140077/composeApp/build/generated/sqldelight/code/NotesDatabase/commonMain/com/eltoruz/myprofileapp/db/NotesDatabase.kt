package com.eltoruz.myprofileapp.db

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.eltoruz.myprofileapp.db.composeApp.newInstance
import com.eltoruz.myprofileapp.db.composeApp.schema
import kotlin.Unit

public interface NotesDatabase : Transacter {
  public val noteEntityQueries: NoteEntityQueries

  public companion object {
    public val Schema: SqlSchema<QueryResult.Value<Unit>>
      get() = NotesDatabase::class.schema

    public operator fun invoke(driver: SqlDriver): NotesDatabase =
        NotesDatabase::class.newInstance(driver)
  }
}
