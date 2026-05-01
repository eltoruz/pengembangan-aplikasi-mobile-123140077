package com.eltoruz.myprofileapp.db

import kotlin.Long
import kotlin.String

public data class NoteEntity(
  public val id: Long,
  public val title: String,
  public val content: String,
  public val created_at: Long,
  public val updated_at: Long,
  public val is_favorite: Long,
)
