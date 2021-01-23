package edu.nuce.apps.demo.data.models

import com.google.gson.annotations.SerializedName

data class User(
    val id: Long,
    val login: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
