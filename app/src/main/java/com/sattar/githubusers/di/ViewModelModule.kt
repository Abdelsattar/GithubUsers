package com.sattar.githubusers.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sattar.githubusers.ui.home.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
 @Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}