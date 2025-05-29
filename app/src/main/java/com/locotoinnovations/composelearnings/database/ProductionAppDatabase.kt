package com.locotoinnovations.composelearnings.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.locotoinnovations.composelearnings.database.post.PostDao
import com.locotoinnovations.composelearnings.database.post.PostEntity
import com.locotoinnovations.composelearnings.database.post.PostPageKeyEntity

@Database(
    entities = [
        PostEntity::class,
        PostPageKeyEntity::class,
    ], version = 2,
)
abstract class ProductionAppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
}