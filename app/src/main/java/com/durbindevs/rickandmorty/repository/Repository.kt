package com.durbindevs.rickandmorty.repository

import com.durbindevs.rickandmorty.api.RetrofitInstance
import com.durbindevs.rickandmorty.db.CharacterDatabase
import com.durbindevs.rickandmorty.models.Result

class Repository(val db: CharacterDatabase) {



    suspend fun getAllCharacters(page: String) =
        RetrofitInstance.api.getAllCharacters(page)

    suspend fun getAllLocations(page: String) =
        RetrofitInstance.api.getAllLocations(page)

    suspend fun searchCharacters(search: String) =
        RetrofitInstance.api.searchCharacters(search)

    suspend fun upsert(result: Result) = db.getCharacterDao().upsert(result)

    fun getSavedCharacters() = db.getCharacterDao().getAllCharacters()
}