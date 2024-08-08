package com.locotoinnovations.composelearnings.database.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    suspend fun getAll(): List<PostEntity>

    @Upsert
    suspend fun insertAll(posts: List<PostEntity>)
}