package com.sattar.githubusers.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sattar.githubusers.KEY_USER
import com.sattar.githubusers.R
import com.sattar.githubusers.data.State
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.databinding.ActivityUsersListBinding
import dagger.android.AndroidInjection
import java.io.Serializable
import javax.inject.Inject

class UsersListActivity : AppCompatActivity(), onItemClickListener {

    //    private lateinit var viewModel: UsersListViewModel
    private lateinit var usersListAdapter: UsersListAdapter
    var isList = true

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var viewModel: UsersListViewModel

    private lateinit var binding: ActivityUsersListBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        binding = ActivityUsersListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initAdapter()
        initState()
    }


    private fun initAdapter() {
        usersListAdapter = UsersListAdapter { viewModel.retry() }
        usersListAdapter.onCLickListener(this)
        binding.rvUsers.adapter = usersListAdapter
        viewModel.usersList.observe(this,
            Observer {
                usersListAdapter.submitList(it)
            })


    }

    private fun initState() {
        binding.txtError.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            binding.progressBar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) VISIBLE else GONE
            binding.txtError.visibility =
                if (viewModel.listIsEmpty() && state == State.ERROR) VISIBLE else GONE
            if (!viewModel.listIsEmpty()) {
                usersListAdapter.setState(state ?: State.DONE)
            }
        })
    }

    fun onChangeLayoutClicked(view: View) {

        if (isList) {
            //change to grid view
            binding.imgListState.setImageResource(R.drawable.ic_list_24)
            binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            //change to list view
            binding.imgListState.setImageResource(R.drawable.ic_grid_24)
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }

        usersListAdapter.notifyDataSetChanged()
        isList = !isList


    }

    override fun onItemClicked(user: User) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra(KEY_USER, user as Serializable)
        startActivity(intent)
    }
}