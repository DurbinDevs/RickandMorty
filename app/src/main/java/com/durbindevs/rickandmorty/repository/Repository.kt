package com.durbindevs.rickandmorty.repository

import com.durbindevs.rickandmorty.api.RetrofitInstance

class Repository {

    suspend fun getAllCharacters(page: String) =
        RetrofitInstance.api.getAllCharacters(page)

    suspend fun getAllLocations(page: String) =
        RetrofitInstance.api.getAllLocations(page)

    suspend fun searchCharacters(search: String) =
        RetrofitInstance.api.searchCharacters(search)


}