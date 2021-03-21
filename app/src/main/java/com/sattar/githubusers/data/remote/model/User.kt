package com.sattar.githubusers.data.remote.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.gson.annotations.SerializedName
import com.sattar.githubusers.R
import com.squareup.picasso.Picasso
import java.io.Serializable


data class User(
    @SerializedName("avatar_url")
    val image: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val id: Int,
    @SerializedName("login")
    val userName: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String,
    val bio: String,
    val blog: String,
    val company: String,
    val email: String,
    val followers: Int,
    val following: Int,
    val location: String,
    val name: String,
    @SerializedName("public_gists")
    val publicGists: Int,
    @SerializedName("public_repos")
    val publicRepos: Int

) : Serializable

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, imageUrl: String?) {
    Picasso.get().load(imageUrl).placeholder(R.drawable.ic_deafult_avatar)
        .error(R.drawable.ic_deafult_avatar).into(imageView)
}