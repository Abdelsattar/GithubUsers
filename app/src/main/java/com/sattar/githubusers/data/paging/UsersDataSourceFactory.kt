package com.sattar.githubusers.data.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.data.remote.service.UsersService
import com.sattar.githubusers.di.BaseSchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UsersDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val usersService: UsersService,
    private val baseSchedulerProvider: BaseSchedulerProvider
) : DataSource.Factory<Int, User>() {

    val usersDataSourceLiveData = MutableLiveData<UsersDataSource>()

    override fun create(): DataSource<Int, User> {
        val usersDataSource = UsersDataSource(
            usersService,
            compositeDisposable,
            baseSchedulerProvider
        )
        usersDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }
}