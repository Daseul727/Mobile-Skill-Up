package com.example.showdogandmove.data.remote

import com.example.showdogandmove.model.DogResponse
import com.example.showdogandmove.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET(Constants.RANDOM_URL)
    suspend fun getDog(): Response<DogResponse>
}