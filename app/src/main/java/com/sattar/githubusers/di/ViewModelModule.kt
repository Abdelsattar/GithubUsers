package com.sattar.githubusers.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sattar.githubusers.ui.userDetails.UserDetailsViewModel
import com.sattar.githubusers.ui.userslist.UsersListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UsersListViewModel::class)
    abstract fun bindUsersListViewModel(viewModel: UsersListViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(UserDetailsViewModel::class)
    abstract fun bindUserDetailsViewModel(viewModel: UserDetailsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}