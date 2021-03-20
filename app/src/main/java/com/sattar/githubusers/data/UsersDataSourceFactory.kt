package com.sattar.githubusers.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sattar.githubusers.data.UsersDataSource
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.data.remote.service.UsersService
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UsersDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val usersService: UsersService
)
    : DataSource.Factory<Int, User>() {

    val usersDataSourceLiveData = MutableLiveData<UsersDataSource>()

    override fun create(): DataSource<Int, User> {
        val usersDataSource = UsersDataSource(usersService, compositeDisposable)
        usersDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }
}