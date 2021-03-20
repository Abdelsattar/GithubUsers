package com.sattar.githubusers.data.remote

import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.data.remote.service.UsersService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class UsersRepo @Inject constructor(private val usersService: UsersService) {


    fun getUsers(since: Int, perPage: Int): Single<List<User>> {
        return usersService.getUsers(since, perPage)
    }
}