package com.sattar.githubusers.data

import com.google.gson.annotations.SerializedName

data class News(
        @SerializedName("login")
        val title: String,
        @SerializedName("avatar_url")
        val image: String?
)