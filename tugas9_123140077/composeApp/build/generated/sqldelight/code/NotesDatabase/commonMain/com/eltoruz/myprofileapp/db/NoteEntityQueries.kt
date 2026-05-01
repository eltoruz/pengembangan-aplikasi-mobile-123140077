package com.eltoruz.myprofileapp.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.TransacterImpl
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlCursor
import app.cash.sqldelight.db.SqlDriver
import kotlin.Any
import kotlin.Long
import kotlin.String

public class NoteEntityQueries(
  driver: SqlDriver,
) : TransacterImpl(driver) {
  public fun <T : Any> selectAll(mapper: (
    id: Long,
    title: String,
    content: String,
    created_at: Long,
    updated_at: Long,
    is_favorite: Long,
  ) -> T): Query<T> = Query(2_071_782_634, arrayOf("NoteEntity"), driver, "NoteEntity.sq",
      "selectAll",
      "SELECT NoteEntity.id, NoteEntity.title, NoteEntity.content, NoteEntity.created_at, NoteEntity.updated_at, NoteEntity.is_favorite FROM NoteEntity ORDER BY updated_at DESC") {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!,
      cursor.getLong(5)!!
    )
  }

  public fun selectAll(): Query<NoteEntity> = selectAll { id, title, content, created_at,
      updated_at, is_favorite ->
    NoteEntity(
      id,
      title,
      content,
      created_at,
      updated_at,
      is_favorite
    )
  }

  public fun <T : Any> selectAllByTitle(mapper: (
    id: Long,
    title: String,
    content: String,
    created_at: Long,
    updated_at: Long,
    is_favorite: Long,
  ) -> T): Query<T> = Query(-680_231_945, arrayOf("NoteEntity"), driver, "NoteEntity.sq",
      "selectAllByTitle",
      "SELECT NoteEntity.id, NoteEntity.title, NoteEntity.content, NoteEntity.created_at, NoteEntity.updated_at, NoteEntity.is_favorite FROM NoteEntity ORDER BY title ASC") {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!,
      cursor.getLong(5)!!
    )
  }

  public fun selectAllByTitle(): Query<NoteEntity> = selectAllByTitle { id, title, content,
      created_at, updated_at, is_favorite ->
    NoteEntity(
      id,
      title,
      content,
      created_at,
      updated_at,
      is_favorite
    )
  }

  public fun <T : Any> selectAllByCreatedAt(mapper: (
    id: Long,
    title: String,
    content: String,
    created_at: Long,
    updated_at: Long,
    is_favorite: Long,
  ) -> T): Query<T> = Query(1_937_170_266, arrayOf("NoteEntity"), driver, "NoteEntity.sq",
      "selectAllByCreatedAt",
      "SELECT NoteEntity.id, NoteEntity.title, NoteEntity.content, NoteEntity.created_at, NoteEntity.updated_at, NoteEntity.is_favorite FROM NoteEntity ORDER BY created_at DESC") {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!,
      cursor.getLong(5)!!
    )
  }

  public fun selectAllByCreatedAt(): Query<NoteEntity> = selectAllByCreatedAt { id, title, content,
      created_at, updated_at, is_favorite ->
    NoteEntity(
      id,
      title,
      content,
      created_at,
      updated_at,
      is_favorite
    )
  }

  public fun <T : Any> selectById(id: Long, mapper: (
    id: Long,
    title: String,
    content: String,
    created_at: Long,
    updated_at: Long,
    is_favorite: Long,
  ) -> T): Query<T> = SelectByIdQuery(id) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!,
      cursor.getLong(5)!!
    )
  }

  public fun selectById(id: Long): Query<NoteEntity> = selectById(id) { id_, title, content,
      created_at, updated_at, is_favorite ->
    NoteEntity(
      id_,
      title,
      content,
      created_at,
      updated_at,
      is_favorite
    )
  }

  public fun <T : Any> search(
    title: String,
    content: String,
    mapper: (
      id: Long,
      title: String,
      content: String,
      created_at: Long,
      updated_at: Long,
      is_favorite: Long,
    ) -> T,
  ): Query<T> = SearchQuery(title, content) { cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!,
      cursor.getLong(5)!!
    )
  }

  public fun search(title: String, content: String): Query<NoteEntity> = search(title, content) {
      id, title_, content_, created_at, updated_at, is_favorite ->
    NoteEntity(
      id,
      title_,
      content_,
      created_at,
      updated_at,
      is_favorite
    )
  }

  public fun <T : Any> selectFavorites(mapper: (
    id: Long,
    title: String,
    content: String,
    created_at: Long,
    updated_at: Long,
    is_favorite: Long,
  ) -> T): Query<T> = Query(-1_724_024_096, arrayOf("NoteEntity"), driver, "NoteEntity.sq",
      "selectFavorites",
      "SELECT NoteEntity.id, NoteEntity.title, NoteEntity.content, NoteEntity.created_at, NoteEntity.updated_at, NoteEntity.is_favorite FROM NoteEntity WHERE is_favorite = 1 ORDER BY updated_at DESC") {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getString(2)!!,
      cursor.getLong(3)!!,
      cursor.getLong(4)!!,
      cursor.getLong(5)!!
    )
  }

  public fun selectFavorites(): Query<NoteEntity> = selectFavorites { id, title, content,
      created_at, updated_at, is_favorite ->
    NoteEntity(
      id,
      title,
      content,
      created_at,
      updated_at,
      is_favorite
    )
  }

  public fun insert(
    title: String,
    content: String,
    created_at: Long,
    updated_at: Long,
  ) {
    driver.execute(-1_431_205_132, """
        |INSERT INTO NoteEntity(title, content, created_at, updated_at, is_favorite)
        |VALUES (?, ?, ?, ?, 0)
        """.trimMargin(), 4) {
          bindString(0, title)
          bindString(1, content)
          bindLong(2, created_at)
          bindLong(3, updated_at)
        }
    notifyQueries(-1_431_205_132) { emit ->
      emit("NoteEntity")
    }
  }

  public fun update(
    title: String,
    content: String,
    updated_at: Long,
    id: Long,
  ) {
    driver.execute(-1_086_258_940,
        """UPDATE NoteEntity SET title = ?, content = ?, updated_at = ? WHERE id = ?""", 4) {
          bindString(0, title)
          bindString(1, content)
          bindLong(2, updated_at)
          bindLong(3, id)
        }
    notifyQueries(-1_086_258_940) { emit ->
      emit("NoteEntity")
    }
  }

  public fun delete(id: Long) {
    driver.execute(-1_582_871_066, """DELETE FROM NoteEntity WHERE id = ?""", 1) {
          bindLong(0, id)
        }
    notifyQueries(-1_582_871_066) { emit ->
      emit("NoteEntity")
    }
  }

  public fun toggleFavorite(id: Long) {
    driver.execute(-402_476_469,
        """UPDATE NoteEntity SET is_favorite = CASE WHEN is_favorite = 0 THEN 1 ELSE 0 END WHERE id = ?""",
        1) {
          bindLong(0, id)
        }
    notifyQueries(-402_476_469) { emit ->
      emit("NoteEntity")
    }
  }

  private inner class SelectByIdQuery<out T : Any>(
    public val id: Long,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("NoteEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("NoteEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-199_206_487,
        """SELECT NoteEntity.id, NoteEntity.title, NoteEntity.content, NoteEntity.created_at, NoteEntity.updated_at, NoteEntity.is_favorite FROM NoteEntity WHERE id = ?""",
        mapper, 1) {
      bindLong(0, id)
    }

    override fun toString(): String = "NoteEntity.sq:selectById"
  }

  private inner class SearchQuery<out T : Any>(
    public val title: String,
    public val content: String,
    mapper: (SqlCursor) -> T,
  ) : Query<T>(mapper) {
    override fun addListener(listener: Query.Listener) {
      driver.addListener("NoteEntity", listener = listener)
    }

    override fun removeListener(listener: Query.Listener) {
      driver.removeListener("NoteEntity", listener = listener)
    }

    override fun <R> execute(mapper: (SqlCursor) -> QueryResult<R>): QueryResult<R> =
        driver.executeQuery(-1_153_749_533,
        """SELECT NoteEntity.id, NoteEntity.title, NoteEntity.content, NoteEntity.created_at, NoteEntity.updated_at, NoteEntity.is_favorite FROM NoteEntity WHERE title LIKE ? OR content LIKE ? ORDER BY updated_at DESC""",
        mapper, 2) {
      bindString(0, title)
      bindString(1, content)
    }

    override fun toString(): String = "NoteEntity.sq:search"
  }
}
