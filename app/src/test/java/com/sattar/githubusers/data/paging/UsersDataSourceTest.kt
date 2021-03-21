package com.sattar.githubusers.data.paging

import com.sattar.githubusers.data.remote.service.UsersService
import com.sattar.githubusers.di.TestSchedulerProvider
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.Mock

internal class UsersDataSourceTest {

    @Mock
    private lateinit var usersService: UsersService

    @Mock
    private lateinit var compositeDisposable: CompositeDisposable


    private lateinit var subject: UsersDataSource

    private var testScheduler = TestSchedulerProvider(TestScheduler())

    @BeforeAll
    fun setUp() {

        subject = UsersDataSource(usersService, compositeDisposable, testScheduler)
    }

    @Test
    fun loadInitial() {

    }

    @Test
    fun loadAfter() {
    }

    @Test
    fun loadBefore() {
    }

    @Test
    fun retry() {
    }
}