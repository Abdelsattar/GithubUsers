package com.sattar.githubusers.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sattar.githubusers.R
import com.sattar.githubusers.data.remote.model.ResultResource
import com.sattar.githubusers.databinding.ActivityMainBinding
import com.sattar.githubusers.lazyThreadSafetyNone
import dagger.android.AndroidInjection
import javax.inject.Inject


class MainActivity : AppCompatActivity() {


//    private lateinit var userAdapter: UserRecyclerViewAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var mainViewModel: MainViewModel

    //    private lateinit var binding: ActivityMainBinding
    var isList = true

    private val binder by lazyThreadSafetyNone<ActivityMainBinding> {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

//    private val viewModel by lazyThreadSafetyNone {
//        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binder.rvUsers.layoutManager = LinearLayoutManager(this)
//        userAdapter = UserRecyclerViewAdapter()
//        binder.viewModel = viewModel
//        binder.adapter = userAdapter

        initScreen()
    }

    private fun initScreen() {
        AndroidInjection.inject(this)

        mainViewModel.getUsers().observe(this, Observer {
            when (it) {
                is ResultResource.Success -> {
                    Log.e("Main", "Success")
                    Log.e("Main", "${it.value.size}")
//                    it.value.let(userAdapter::submitList)

//                loading.cancel()
//                if (it.value.code == 200) {
//                    buildHomeCatsRv(it.value.data)
//                } else
//                    requireView().snackBar(it.value.validation())
                }
                is ResultResource.Failure -> {
                    Log.e("Main", "failure")

//                loading.cancel()
//                handleApiErrors(it)
                }
                is ResultResource.Loading -> {
//                loading.show()
                    Log.e("Main", "loading")

                }
            }
        })
    }



}