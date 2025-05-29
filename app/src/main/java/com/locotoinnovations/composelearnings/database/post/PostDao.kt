package com.locotoinnovations.composelearnings.database.post

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    suspend fun getAll(): List<PostEntity>

    @Upsert
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("SELECT * FROM post")
    fun readPostsPagingSource(): PagingSource<Int, PostEntity>

    @Query("select next_page_key from PostPageKey ORDER BY next_page_key DESC limit 1")
    suspend fun getNextPostPageKey(): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPostPageKey(postPageKeyEntity: PostPageKeyEntity)
}