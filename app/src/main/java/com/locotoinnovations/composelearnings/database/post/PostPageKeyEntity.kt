package com.locotoinnovations.composelearnings.database.post

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PostPageKeyEntity.TABLE_NAME)
data class PostPageKeyEntity(
    @PrimaryKey @ColumnInfo(Column.NEXT_PAGE_KEY) val nextPageKey: Int,
) {
    companion object {
        const val TABLE_NAME = "PostPageKey"
    }

    internal object Column {
        const val NEXT_PAGE_KEY = "next_page_key"
    }
}
