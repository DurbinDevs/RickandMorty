package com.durbindevs.rickandmorty.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.durbindevs.rickandmorty.locationModels.Locations
import com.durbindevs.rickandmorty.models.Characters
import com.durbindevs.rickandmorty.models.Result
import com.durbindevs.rickandmorty.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val characterResponse: MutableLiveData<Response<Characters>> = MutableLiveData()
    val characterSearch: MutableLiveData<Response<Characters>> = MutableLiveData()
    val locationResponse: MutableLiveData<Response<Locations>> = MutableLiveData()

    companion object {

        var pageNumber = 1
    }

    init {
        getAllCharacters(pageNumber.toString())
    }

    var characterResult: Response<Characters>? = null
    var locationResult: Response<Locations>? = null


    fun getAllCharacters(page: String) {
        viewModelScope.launch {
            characterCall()
        }
    }

    fun getAllLocations(page: String) {
        viewModelScope.launch {
         locationCall()
        }
    }

    fun searchCharacters(search: String) {
        viewModelScope.launch {
            val response = repository.searchCharacters(search)
            characterSearch.value = response
        }
    }

    fun saveCharacter(result: Result) = viewModelScope.launch {
       repository.upsert(result)
    }

    fun deleteCharacters(result: Result) = viewModelScope.launch {
        repository.deleteCharacters(result)
    }

    fun getSavedCharacters() = repository.getSavedCharacters()

    private fun handleGetAllCharacters(response: Response<Characters>): Response<Characters> {
        if (response.isSuccessful && characterResult == null) {
            characterResult = response
        } else {
            val result = response.body()?.results
            val oldList = characterResult!!.body()?.results
            oldList?.addAll(result!!.toList())
            //  return characterResult!!
        }
        return characterResult!!
    }

    private suspend fun characterCall() {
        Log.d("test", "character call: ${pageNumber}")
        val response = repository.getAllCharacters(pageNumber.toString())
        characterResponse.postValue(handleGetAllCharacters(response))
    }

    private fun handleGetAllLocations(response: Response<Locations>): Response<Locations> {
        if (response.isSuccessful && locationResult == null) {
            locationResult = response
        } else {
            val result = response.body()?.results
            val oldList = locationResult!!.body()?.results
            val newList = result
            Log.d("test", "${oldList}")
            Log.d("test", "${result}")
            oldList?.addAll(newList!!.toList())
            //  return characterResult!!
            Log.d("test", "add")
        }
        return locationResult!!
    }

    private suspend fun locationCall() {
        Log.d("test", "location call: ${pageNumber}")
        val response = repository.getAllLocations(pageNumber.toString())
        locationResponse.postValue(handleGetAllLocations(response))
    }

}