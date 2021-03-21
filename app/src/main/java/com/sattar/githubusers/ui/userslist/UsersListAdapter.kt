package com.sattar.githubusers.ui.userslist

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sattar.githubusers.data.paging.State
import com.sattar.githubusers.data.remote.model.User

interface onItemClickListener {
    fun onItemClicked(user: User)
}

class UsersListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<User, RecyclerView.ViewHolder>(NewsDiffCallback) {

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

}