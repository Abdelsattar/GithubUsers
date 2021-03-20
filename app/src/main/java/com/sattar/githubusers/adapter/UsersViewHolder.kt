package com.sattar.githubusers.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sattar.githubusers.R
import com.sattar.githubusers.data.News
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UsersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(news: News?) {
        if (news != null) {
            itemView.txtUserName.text = news.title
            if (!news.image.isNullOrEmpty())
                Picasso.get().load(news.image).into(itemView.imgUser)
        }
    }

    companion object {
        fun create(parent: ViewGroup): UsersViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
            return UsersViewHolder(view)
        }
    }
}