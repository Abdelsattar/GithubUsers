package com.sattar.githubusers.di

import com.sattar.githubusers.data.remote.reposotires.UsersRepository
import com.sattar.githubusers.data.remote.service.UsersService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun getPhotosRepository(usersService: UsersService): UsersRepository {
        return UsersRepository(usersService)
    }

}