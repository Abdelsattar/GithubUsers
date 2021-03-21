package com.sattar.githubusers.data.remote.model

sealed class ResultResource<out T> {

    data class Success<out T>(val value: T) : ResultResource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: String?
    ) : ResultResource<Nothing>()

    object Loading : ResultResource<Nothing>()
}