package com.durbindevs.rickandmorty.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.durbindevs.rickandmorty.locationModels.Locations
import com.durbindevs.rickandmorty.models.Characters
import com.durbindevs.rickandmorty.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val characterResponse: MutableLiveData<Response<Characters>> = MutableLiveData()
    val characterSearch: MutableLiveData<Response<Characters>> = MutableLiveData()
    val locationResponse: MutableLiveData<Response<Locations>> = MutableLiveData()

    fun getAllCharacters() {
        viewModelScope.launch {
            val response = repository.getAllCharacters()
            characterResponse.value = response
        }
    }

    fun getAllLocations() {
        viewModelScope.launch {
            val response = repository.getAllLocations()
            locationResponse.value = response
        }
    }

    fun searchCharacters(search: String) {
        viewModelScope.launch {
            val response = repository.searchCharacters(search)
            characterSearch.value = response
        }
    }
}