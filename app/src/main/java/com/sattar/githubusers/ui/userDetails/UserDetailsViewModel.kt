package com.sattar.githubusers.ui.userDetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sattar.githubusers.data.remote.model.ResultResource
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.data.remote.reposotires.UsersRepository
import com.sattar.githubusers.di.BaseSchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Sattar on 20.03.21.
 */
class UserDetailsViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val schedulerProvider: BaseSchedulerProvider
) : ViewModel() {


    private val disposables = CompositeDisposable()
    private val usersResponse = MutableLiveData<ResultResource<User>>()

    fun getUser(userName: String): MutableLiveData<ResultResource<User>> {

        disposables.add(
            usersRepository.getUser(userName)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ response: User ->

                    usersResponse.value = ResultResource.Success(response)
                    Log.e("UserResponse", "${usersResponse.value}" )

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