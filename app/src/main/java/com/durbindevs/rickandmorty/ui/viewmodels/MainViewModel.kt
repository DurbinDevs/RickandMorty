package com.durbindevs.rickandmorty.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.durbindevs.rickandmorty.models.Characters
import com.durbindevs.rickandmorty.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository): ViewModel() {

    val _response: MutableLiveData<Response<Characters>> = MutableLiveData()

    fun getAllCharacters() {
        Log.d("test", "1st part response is :")
        viewModelScope.launch {
            val response = repository.getAllCharacters()
            _response.value = response
            Log.d("test", "viewmodel response is : $response")
        }
    }
}