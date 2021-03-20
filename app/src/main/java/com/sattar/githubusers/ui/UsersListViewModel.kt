package com.sharmadhiraj.androidpaginglibrarystepbystepimplementationguide.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sattar.githubusers.data.NetworkService
import com.sattar.githubusers.data.State
import com.sattar.githubusers.data.UsersDataSource
import com.sattar.githubusers.data.remote.model.User
import com.sharmadhiraj.androidpaginglibrarystepbystepimplementationguide.data.UsersDataSourceFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable

class UsersListViewModel : ViewModel() {

    private val networkService = NetworkService.getService()
    var usersList: LiveData<PagedList<User>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 14
    private val usersDataSourceFactory: UsersDataSourceFactory

    init {
        usersDataSourceFactory = UsersDataSourceFactory(compositeDisposable, networkService)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 3)
            .setEnablePlaceholders(false)
            .build()
        usersList = LivePagedListBuilder(usersDataSourceFactory, config).build()
    }


    fun getState(): LiveData<State> = Transformations.switchMap(
        usersDataSourceFactory.usersDataSourceLiveData,
        UsersDataSource::state
    )

    fun retry() {
        usersDataSourceFactory.usersDataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return usersList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}