package com.sattar.githubusers.data.remote

import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.data.remote.service.UsersService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Sattar on 16.03.21.
 */
class UsersRepo @Inject constructor(private val usersService: UsersService) {


    fun getUsers(since: Int, perPage: Int): Single<List<User>> {

        return usersService.getUsers(since, perPage)
    }
}