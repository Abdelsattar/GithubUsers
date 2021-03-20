package com.sharmadhiraj.androidpaginglibrarystepbystepimplementationguide.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sattar.githubusers.data.NetworkService
import com.sattar.githubusers.data.UsersDataSource
import com.sattar.githubusers.data.remote.model.User
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UsersDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
)
    : DataSource.Factory<Int, User>() {

    val usersDataSourceLiveData = MutableLiveData<UsersDataSource>()

    override fun create(): DataSource<Int, User> {
        val usersDataSource = UsersDataSource(networkService, compositeDisposable)
        usersDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }
}