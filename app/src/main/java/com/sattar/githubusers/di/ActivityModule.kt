package com.sattar.githubusers.di

import com.example.mvvmstarter.ui.home.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

 @Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity?
}