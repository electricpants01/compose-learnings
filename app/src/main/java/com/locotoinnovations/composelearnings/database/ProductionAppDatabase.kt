package com.locotoinnovations.composelearnings.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.locotoinnovations.composelearnings.database.post.PostDao
import com.locotoinnovations.composelearnings.database.post.PostEntity

@Database(
    entities = [
        PostEntity::class,
    ], version = 1,
)
abstract class ProductionAppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
}