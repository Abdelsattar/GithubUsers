package com.sharmadhiraj.androidpaginglibrarystepbystepimplementationguide.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sattar.githubusers.data.NetworkService
import com.sattar.githubusers.data.News
import com.sattar.githubusers.data.UsersDataSource
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UsersDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
)
    : DataSource.Factory<Int, News>() {

    val newsDataSourceLiveData = MutableLiveData<UsersDataSource>()

    override fun create(): DataSource<Int, News> {
        val newsDataSource = UsersDataSource(networkService, compositeDisposable)
        newsDataSourceLiveData.postValue(newsDataSource)
        return newsDataSource
    }
}