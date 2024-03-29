package com.example.network_sample.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network_sample.data.Repository
import com.example.network_sample.utils.NetworkResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _response : MutableLiveData<NetworkResult<DogResponse>> = MutableLiveData()
    val response : LiveData<NetworkResult<DogResponse>> = _response

    fun fetchDogResponse() = viewModelScope.launch {
        repository.getDog().collect { values ->
            _response.value = values
        }
    }
}