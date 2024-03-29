package com.example.network_sample.data.remote

import com.example.network_sample.model.DogResponse
import com.example.network_sample.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET(Constants.RANDOM_URL)
    suspend fun getDog(): Response<DogResponse>
}