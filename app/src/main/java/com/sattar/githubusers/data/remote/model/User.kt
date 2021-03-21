package com.sattar.githubusers.data.remote.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import com.sattar.githubusers.R
import com.squareup.picasso.Picasso
import java.io.Serializable


data class User(
    @SerializedName("avatar_url")
    val image: String = "",
    val events_url: String = "",
    val followers_url: String = "",
    val following_url: String = "",
    val id: Int = 0,
    @SerializedName("login")
    val userName: String = "",
    val url: String = "",
    val bio: String = "",
    val blog: String = "",
    val company: String = "",
    val email: String = "",
    val followers: Int = 0,
    val following: Int = 0,
    val location: String = "",
    val name: String = ""

) : Serializable

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Picasso.get().load(imageUrl).placeholder(R.drawable.ic_deafult_avatar)
        .error(R.drawable.ic_deafult_avatar).into(imageView)
}