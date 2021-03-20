package com.sharmadhiraj.androidpaginglibrarystepbystepimplementationguide.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sattar.githubusers.data.NetworkService
import com.sattar.githubusers.data.News
import com.sattar.githubusers.data.UsersDataSource
import com.sattar.githubusers.data.State
import com.sharmadhiraj.androidpaginglibrarystepbystepimplementationguide.data.*
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UsersListViewModel : ViewModel() {

    private val networkService = NetworkService.getService()
    var newsList: LiveData<PagedList<News>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 10
    private val newsDataSourceFactory:UsersDataSourceFactory

    init {
        newsDataSourceFactory = UsersDataSourceFactory(compositeDisposable, networkService)
        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setEnablePlaceholders(false)
                .build()
        newsList = LivePagedListBuilder(newsDataSourceFactory, config).build()
    }


    fun getState(): LiveData<State> = Transformations.switchMap(
            newsDataSourceFactory.newsDataSourceLiveData,
            UsersDataSource::state
    )

    fun retry() {
        newsDataSourceFactory.newsDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return newsList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}