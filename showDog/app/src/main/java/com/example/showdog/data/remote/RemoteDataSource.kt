package com.example.showdog.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val dogService: APIService) {

    suspend fun getDog() = dogService.getDog()
}