package com.durbindevs.rickandmorty.repository

import com.durbindevs.rickandmorty.api.RetrofitInstance

class Repository {

    suspend fun getAllCharacters() =
        RetrofitInstance.api.getAllCharacters()

}