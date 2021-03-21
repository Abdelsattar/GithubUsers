package com.sattar.githubusers.ui.userdetails

import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.verify
import com.sattar.githubusers.InstantExecutorExtension
import com.sattar.githubusers.data.remote.model.ResultResource
import com.sattar.githubusers.data.remote.model.User
import com.sattar.githubusers.data.remote.reposotires.UsersRepository
import com.sattar.githubusers.di.TestSchedulerProvider
import com.sattar.githubusers.ui.userDetails.UserDetailsViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.atLeastOnce
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.Answer
import java.io.IOException
import java.util.concurrent.TimeUnit


/**
 * Created by Sattar on 21.03.21.
 */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(InstantExecutorExtension::class)
internal class UserDetailsViewModelTest {

    private var testScheduler = TestScheduler()
    private var schedulerProvider = TestSchedulerProvider(testScheduler)

    @Mock
    lateinit var usersRepository: UsersRepository

    private lateinit var subject: UserDetailsViewModel

    @BeforeAll
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        subject = UserDetailsViewModel(
            usersRepository,
            schedulerProvider
        )
    }

    //region test cases for getLatestCurrencyRates
    @Test
    fun `should success with user returned`() {
        //Given.
        val expectedResponse = ResultResource.Success(getUserFromJson())

        //When
        `when`(
            usersRepository.getUser(ArgumentMatchers.anyString())
        ).thenAnswer {
            Single.just(getUserFromJson())
        }

        val actualResponse = subject.getUser("abdelsattar")

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        //Then
        Assertions.assertEquals(expectedResponse, actualResponse.value)
    }

    @Test
    fun `should call user repository when call getUser method `() {
        `when`(
            usersRepository.getUser(ArgumentMatchers.anyString())
        ).thenAnswer {
            Single.just(User())
        }

        subject.getUser("anything")
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        verify(
            usersRepository,
            atLeastOnce()
        ).getUser(ArgumentMatchers.anyString())
    }

    @Test
    fun `should return failure when service throw an exception from api `() {
        val expectedException = ResultResource.Failure(false,0,"Forbidden")

        `when`(usersRepository.getUser(ArgumentMatchers.anyString()))
            .thenAnswer(Answer { Single.error<Throwable>(Throwable("Forbidden")) })

        val actualResponse = subject.getUser("anything")
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Assertions.assertEquals(expectedException, actualResponse.value)
    }


    @Test
    fun `should return failure with throw Network error `() {
        val expectedException = ResultResource.Failure(true,0,null)

        `when`(usersRepository.getUser(ArgumentMatchers.anyString()))
            .thenAnswer(Answer { Single.error<Exception>(IOException()) })

        val actualResponse = subject.getUser("anything")
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        Assertions.assertEquals(expectedException, actualResponse.value)
    }


    private fun getUserFromJson(): User? {
        return Gson().fromJson(
            "{\n" +
                    "  \"login\": \"Abdelsattar\",\n" +
                    "  \"id\": 9407394,\n" +
                    "  \"node_id\": \"MDQ6VXNlcjk0MDczOTQ=\",\n" +
                    "  \"avatar_url\": \"https://avatars.githubusercontent.com/u/9407394?v=4\",\n" +
                    "  \"gravatar_id\": \"\",\n" +
                    "  \"url\": \"https://api.github.com/users/Abdelsattar\",\n" +
                    "  \"html_url\": \"https://github.com/Abdelsattar\",\n" +
                    "  \"followers_url\": \"https://api.github.com/users/Abdelsattar/followers\",\n" +
                    "  \"following_url\": \"https://api.github.com/users/Abdelsattar/following{/other_user}\",\n" +
                    "  \"gists_url\": \"https://api.github.com/users/Abdelsattar/gists{/gist_id}\",\n" +
                    "  \"starred_url\": \"https://api.github.com/users/Abdelsattar/starred{/owner}{/repo}\",\n" +
                    "  \"subscriptions_url\": \"https://api.github.com/users/Abdelsattar/subscriptions\",\n" +
                    "  \"organizations_url\": \"https://api.github.com/users/Abdelsattar/orgs\",\n" +
                    "  \"repos_url\": \"https://api.github.com/users/Abdelsattar/repos\",\n" +
                    "  \"events_url\": \"https://api.github.com/users/Abdelsattar/events{/privacy}\",\n" +
                    "  \"received_events_url\": \"https://api.github.com/users/Abdelsattar/received_events\",\n" +
                    "  \"type\": \"User\",\n" +
                    "  \"site_admin\": false,\n" +
                    "  \"name\": \"Mohamed Abd EL-Sattar\",\n" +
                    "  \"company\": null,\n" +
                    "  \"blog\": \"https://abdelsattar.github.io/\",\n" +
                    "  \"location\": \"Berlin, Germany\",\n" +
                    "  \"email\": null,\n" +
                    "  \"hireable\": true,\n" +
                    "  \"bio\": \"Android developer loves material design and open source \",\n" +
                    "  \"twitter_username\": null,\n" +
                    "  \"public_repos\": 37,\n" +
                    "  \"public_gists\": 3,\n" +
                    "  \"followers\": 48,\n" +
                    "  \"following\": 96,\n" +
                    "  \"created_at\": \"2014-10-26T19:50:49Z\",\n" +
                    "  \"updated_at\": \"2021-03-19T14:47:48Z\"\n" +
                    "}", User::class.java
        )
    }
}