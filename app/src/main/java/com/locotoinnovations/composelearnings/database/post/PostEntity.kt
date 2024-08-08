package com.locotoinnovations.composelearnings.database.post

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class PostEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)