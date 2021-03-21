package com.sattar.githubusers.data.remote.reposotires

import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.data.remote.service.UsersService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject


class UsersRepository @Inject constructor(private val usersService: UsersService) {

    fun getUser(userName: String): Single<User> {
        return usersService.getUser(userName)
    }
}