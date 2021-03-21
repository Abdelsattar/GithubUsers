package com.sattar.githubusers.di

import com.sattar.githubusers.ui.userDetails.UserDetailsActivity
import com.sattar.githubusers.ui.userslist.UsersListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

 @Module
abstract class ActivityModule {


    @ContributesAndroidInjector
    abstract fun contributeUsersListActivity(): UsersListActivity?


    @ContributesAndroidInjector
    abstract fun contributeUsersDetailsActivity(): UserDetailsActivity?
}