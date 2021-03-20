package com.sattar.githubusers.di

import com.sattar.githubusers.ui.UsersListActivity
import com.sattar.githubusers.ui.home.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

 @Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity?


    @ContributesAndroidInjector
    abstract fun contributeUsersListActivity(): UsersListActivity?
}