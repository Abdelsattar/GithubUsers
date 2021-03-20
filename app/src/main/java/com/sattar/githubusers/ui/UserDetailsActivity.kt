package com.sattar.githubusers.ui

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.sattar.githubusers.KEY_USER
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.databinding.ActivityUserDetailsBinding


class UserDetailsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.extras?.get(KEY_USER) as User
        binding.user = user
        binding.executePendingBindings()


        setSupportActionBar(binding.materialToolBar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}