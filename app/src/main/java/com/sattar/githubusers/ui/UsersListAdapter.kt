package com.sattar.githubusers.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sattar.githubusers.R
import com.sattar.githubusers.data.State
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.databinding.ItemUserBinding

interface onItemClickListener {
    fun onItemClicked(user: User)
}

class UsersListAdapter(private val retry: () -> Unit) :
    PagedListAdapter<User, RecyclerView.ViewHolder>(NewsDiffCallback) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2

    private var state = State.LOADING
    lateinit var mListener: onItemClickListener

    fun onCLickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) {
            UserViewHolder.create(parent)

        } else {
            ListFooterViewHolder.create(
                retry,
                parent
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            val user = getItem(position)
            (holder as UserViewHolder).mBinding.user = user
            holder.mBinding.executePendingBindings()
            holder.itemView.setOnClickListener { view ->
                Log.e("Heelo form inside", "yees")
                user?.let { it -> mListener.onItemClicked(it) }
            }

        } else {
            (holder as ListFooterViewHolder).bind(state)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
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

    class UserViewHolder(var mBinding: ItemUserBinding) : RecyclerView.ViewHolder(mBinding.root) {

        companion object {
            fun create(parent: ViewGroup): UserViewHolder {
                val binding: ItemUserBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_user, parent, false
                    )

                return UserViewHolder(binding)
            }
        }
    }


}