package com.sattar.githubusers.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sattar.githubusers.ui.home.MainViewModel
import com.sattar.githubusers.ui.UsersListViewModel
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
    @IntoMap
    @ViewModelKey(UsersListViewModel::class)
    abstract fun bindUsersListViewModel(viewModel: UsersListViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}