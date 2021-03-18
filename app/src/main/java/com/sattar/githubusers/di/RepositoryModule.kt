package com.sattar.githubusers.di

import com.sattar.githubusers.data.remote.UsersRepo
import com.sattar.githubusers.data.remote.service.UsersService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun getPhotosRepository(usersService: UsersService): UsersRepo {
        return UsersRepo(usersService)
    }

}