package com.locotoinnovations.composelearnings.di

import android.content.Context
import androidx.room.Room
import com.locotoinnovations.composelearnings.database.ProductionAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): ProductionAppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ProductionAppDatabase::class.java,
            "production_app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesPostDao(database: ProductionAppDatabase) = database.postDao()
}