package com.sharmadhiraj.androidpaginglibrarystepbystepimplementationguide.data

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("users")
    fun getNews(
            @Query("since") page: Int,
            @Query("per_page") pageSize: Int
    ): Single<List<News>>

    companion object {
        fun getService(): NetworkService {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(NetworkService::class.java)
        }
    }

}