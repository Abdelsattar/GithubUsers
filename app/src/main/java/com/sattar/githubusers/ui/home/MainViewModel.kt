package com.sattar.githubusers.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sattar.githubusers.data.remote.UsersRepo
import com.sattar.githubusers.data.remote.model.ResultResource
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.di.BaseSchedulerProvider

import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.io.IOException
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val usersRepo: UsersRepo,
    private val schedulerProvider: BaseSchedulerProvider
) : ViewModel() {


    private val disposables = CompositeDisposable()
    private val usersResponse = MutableLiveData<ResultResource<List<User>>>()

    fun getUsers(): MutableLiveData<ResultResource<List<User>>> {
        disposables.add(
            usersRepo.getUsers(0, 20)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ response: List<User> ->

                    usersResponse.value = ResultResource.Success(response)

                }, { t: Throwable? ->
                    usersResponse.value = if (t is IOException) {
                        //network error
                        ResultResource.Failure(true, 0, t.localizedMessage)

                    } else {
                        ResultResource.Failure(false, 0, t?.localizedMessage)

                    }

                })
        )
        return usersResponse
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

}