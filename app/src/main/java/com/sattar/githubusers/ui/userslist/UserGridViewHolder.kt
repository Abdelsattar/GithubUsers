package com.sattar.githubusers.ui.userslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sattar.githubusers.R
import com.sattar.githubusers.databinding.ItemUserGridBinding

class UserGridViewHolder(var mBinding: ItemUserGridBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        companion object {
            fun create(parent: ViewGroup): UserGridViewHolder {
                val binding: ItemUserGridBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_user_grid, parent, false
                    )

                return UserGridViewHolder(
                    binding
                )
            }
        }
    }