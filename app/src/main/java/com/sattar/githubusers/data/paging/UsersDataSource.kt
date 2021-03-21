package com.sattar.githubusers.data.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.sattar.githubusers.data.paging.State.DONE
import com.sattar.githubusers.data.paging.State.ERROR
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.data.remote.service.UsersService
import com.sattar.githubusers.di.BaseSchedulerProvider
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action

class UsersDataSource(
    private val usersService: UsersService,
    private val compositeDisposable: CompositeDisposable,
    private val baseSchedulerProvider: BaseSchedulerProvider
) : PageKeyedDataSource<Int, User>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, User>
    ) {
        updateState(State.LOADING)
        compositeDisposable.add(
            usersService.getUsers(0, params.requestedLoadSize)
                .subscribe(
                    { users ->
                        Log.e("Load size", "${params.requestedLoadSize}")
                        val nexPageKey = users[users.size - 1].id
                        updateState(DONE)
                        callback.onResult(
                            users,
                            null,
                            nexPageKey
                        )
                    },
                    {
                        updateState(ERROR)
                        setRetry(Action { loadInitial(params, callback) })
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            usersService.getUsers(params.key, params.requestedLoadSize)
                .subscribe(
                    { users ->
                        updateState(DONE)
                        val nexPageKey = users[users.size - 1].id

                        callback.onResult(
                            users,
                            nexPageKey
                        )
                    },
                    {
                        updateState(ERROR)
                        setRetry(Action { loadAfter(params, callback) })
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, User>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(baseSchedulerProvider.io())
                    .observeOn(baseSchedulerProvider.ui())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }

}