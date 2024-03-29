package com.example.network_sample.data

import com.example.network_sample.data.remote.RemoteDataSource
import com.example.network_sample.model.BaseApiResponse
import com.example.network_sample.model.DogResponse
import com.example.network_sample.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): BaseApiResponse() {

    suspend fun getDog(): Flow<NetworkResult<DogResponse>> {
        return flow {
            emit(safeApiCall { remoteDataSource.getDog() })
        }.flowOn(Dispatchers.IO)
    }

}