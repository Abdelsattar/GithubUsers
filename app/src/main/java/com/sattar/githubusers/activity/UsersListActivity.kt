package com.sattar.githubusers.activity

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sattar.githubusers.R
import com.sattar.githubusers.adapter.UsersListAdapter
import com.sattar.githubusers.data.State
import com.sharmadhiraj.androidpaginglibrarystepbystepimplementationguide.viewModel.UsersListViewModel
import kotlinx.android.synthetic.main.activity_news_list.*

class UsersListActivity : AppCompatActivity() {

    private lateinit var viewModel: UsersListViewModel
    private lateinit var usersListAdapter: UsersListAdapter
    var isList = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)

        viewModel = ViewModelProvider(this).get(UsersListViewModel::class.java)
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        usersListAdapter = UsersListAdapter { viewModel.retry() }
        rvUsers.adapter = usersListAdapter
        viewModel.newsList.observe(this,
                Observer {
                    usersListAdapter.submitList(it)
                })
    }

    private fun initState() {
        txtError.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            progressBar.visibility = if (viewModel.listIsEmpty() && state == State.LOADING) VISIBLE else GONE
            txtError.visibility = if (viewModel.listIsEmpty() && state == State.ERROR) VISIBLE else GONE
            if (!viewModel.listIsEmpty()) {
                usersListAdapter.setState(state ?: State.DONE)
            }
        })
    }

    fun onChangeLayoutClicked(view: View) {

        if (isList) {
            //change to grid view
            imgListState.setImageResource(R.drawable.ic_list_24)
            rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            //change to list view
            imgListState.setImageResource(R.drawable.ic_grid_24)
            rvUsers.layoutManager = LinearLayoutManager(this)
        }

        usersListAdapter.notifyDataSetChanged()
        isList = !isList


    }

}