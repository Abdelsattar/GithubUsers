package com.sattar.githubusers.ui.userslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sattar.githubusers.data.paging.State
import com.sattar.githubusers.data.paging.UsersDataSource
import com.sattar.githubusers.data.paging.UsersDataSourceFactory
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.data.remote.service.UsersService
import com.sattar.githubusers.di.BaseSchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class UsersListViewModel @Inject constructor(
    private val usersService: UsersService,
    private val baseSchedulerProvider: BaseSchedulerProvider
) :
    ViewModel() {

    var usersList: LiveData<PagedList<User>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 14
    private val usersDataSourceFactory: UsersDataSourceFactory

    init {
        usersDataSourceFactory =
            UsersDataSourceFactory(
                compositeDisposable,
                usersService,
                baseSchedulerProvider
            )
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 3)
            .setEnablePlaceholders(true)
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
