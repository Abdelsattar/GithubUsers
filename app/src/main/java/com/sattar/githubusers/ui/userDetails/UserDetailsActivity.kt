package com.sattar.githubusers.ui.userDetails

import android.R
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sattar.githubusers.KEY_USER
import com.sattar.githubusers.data.remote.model.ResultResource
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.databinding.ActivityUserDetailsBinding
import dagger.android.AndroidInjection
import javax.inject.Inject


class UserDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var userDetailsViewModel: UserDetailsViewModel


    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initScreen()
    }


    private fun initScreen() {
        val user = intent.extras?.get(KEY_USER) as User
        binding.user = user
        binding.executePendingBindings()


        setSupportActionBar(binding.materialToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        userDetailsViewModel.getUser(user.userName).observe(this, Observer {
            when (it) {
                is ResultResource.Success -> {
                    Log.e("Main", "Success")
                    Log.e("Main", "${it.value}")
                    binding.user = it.value

                }
                is ResultResource.Failure -> {
                    Log.e("Main", "failure")
                }
                is ResultResource.Loading -> {
                    Log.e("Main", "loading")

                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}