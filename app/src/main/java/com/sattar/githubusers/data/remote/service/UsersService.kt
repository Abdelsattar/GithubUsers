package com.sattar.githubusers.data.remote.service

import com.sattar.githubusers.data.remote.model.User
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface UsersService {

    @GET("users")
    fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): Single<List<User>>


    @GET("users/{userName}")
    fun getUser(
        @Path("userName") userName: String
    ): Single<User>
}