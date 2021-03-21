package com.sattar.githubusers.ui.userslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sattar.githubusers.R
import com.sattar.githubusers.data.paging.State
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.databinding.ItemUserGridBinding
import com.sattar.githubusers.databinding.ItemUserListBinding

interface onItemClickListener {
    fun onItemClicked(user: User)
}

class UsersListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<User, RecyclerView.ViewHolder>(NewsDiffCallback) {

    //    private val DATA_VIEW_TYPE = 1
    private val LIST_VIEW_TYPE = 0
    private val GRID_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING
    lateinit var mListener: onItemClickListener

    fun onCLickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LIST_VIEW_TYPE) {
            UserListViewHolder.create(
                parent
            )
        } else if (viewType == GRID_VIEW_TYPE) {
            UserGridViewHolder.create(
                parent
            )
        } else {
            ListFooterViewHolder.create(
                retry,
                parent
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == LIST_VIEW_TYPE) {
            val user = getItem(position)
            (holder as UserListViewHolder).mBinding.user = user
            holder.mBinding.executePendingBindings()
            holder.itemView.setOnClickListener { view ->
                user?.let { it -> mListener.onItemClicked(it) }
            }

        } else if (viewType == GRID_VIEW_TYPE) {
            val user = getItem(position)
            (holder as UserGridViewHolder).mBinding.user = user
            holder.mBinding.executePendingBindings()
            holder.itemView.setOnClickListener { view ->
                user?.let { it -> mListener.onItemClicked(it) }
            }
        } else {
            (holder as ListFooterViewHolder).bind(state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) {
            if (isGridView) {
                GRID_VIEW_TYPE
            } else {
                LIST_VIEW_TYPE
            }
        } else FOOTER_VIEW_TYPE
    }

    var isGridView = false

    fun toggleItemViewType(): Boolean {
        isGridView = !isGridView
        return isGridView
    }

    companion object {
        val NewsDiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.userName == newItem.userName
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && (state == State.LOADING || state == State.ERROR)
    }

    fun setState(state: State) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }

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

}