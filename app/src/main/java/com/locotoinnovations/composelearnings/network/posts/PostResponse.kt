package com.locotoinnovations.composelearnings.network.posts

import com.google.gson.annotations.SerializedName
import com.locotoinnovations.composelearnings.database.post.PostEntity

data class PostResponse(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("userId")
    val userId: Int
){
    fun toPostEntity(): PostEntity {
        return PostEntity(
            id = id,
            title = title,
            body = body,
            userId = userId
        )
    }
}