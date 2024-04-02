package com.example.showdog.data.remote

import com.example.showdog.model.DogResponse
import com.example.showdog.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET(Constants.RANDOM_URL)
    suspend fun getDog(): Response<DogResponse>
}