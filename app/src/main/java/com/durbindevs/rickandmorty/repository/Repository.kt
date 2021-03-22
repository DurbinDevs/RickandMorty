package com.durbindevs.rickandmorty.repository

import com.durbindevs.rickandmorty.api.RetrofitInstance

class Repository {

    suspend fun getAllCharacters() =
        RetrofitInstance.api.getAllCharacters()

    suspend fun getAllLocations() =
        RetrofitInstance.api.getAllLocations()

    suspend fun searchCharacters(search: String) =
        RetrofitInstance.api.searchCharacters(search)


}