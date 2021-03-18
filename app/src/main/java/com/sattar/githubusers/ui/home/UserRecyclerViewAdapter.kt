package com.sattar.githubusers.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sattar.githubusers.R
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.databinding.ItemUserBinding

class UserRecyclerViewAdapter() :
    ListAdapter<User, UserRecyclerViewAdapter.ViewHolder>(UsersDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemUserBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user, parent, false
            )
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val user = getItem(position)
        holder.mBinding.user = user
        holder.mBinding.executePendingBindings()
    }


    class ViewHolder(var mBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(mBinding.root)

}

class UsersDiff : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}