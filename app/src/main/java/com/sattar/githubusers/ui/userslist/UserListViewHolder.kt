package com.sattar.githubusers.ui.userslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sattar.githubusers.R
import com.sattar.githubusers.databinding.ItemUserListBinding

class UserListViewHolder(var mBinding: ItemUserListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        companion object {
            fun create(parent: ViewGroup): UserListViewHolder {
                val binding: ItemUserListBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_user_list, parent, false
                    )

                return UserListViewHolder(
                    binding
                )
            }
        }
    }
