package com.durbindevs.rickandmorty.repository

import com.durbindevs.rickandmorty.api.CharacterApi
import com.durbindevs.rickandmorty.db.CharacterDatabase
import com.durbindevs.rickandmorty.models.Result
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: CharacterApi,
    private val db: CharacterDatabase
) {


    suspend fun getAllCharacters(page: String) =
        api.getAllCharacters(page)

    suspend fun getAllLocations(page: String) =
       api.getAllLocations(page)

    suspend fun searchCharacters(search: String) =
       api.searchCharacters(search)

    suspend fun deleteCharacters(result: Result) =
        db.getCharacterDao().deleteCharacters(result)

    suspend fun upsert(result: Result) = db.getCharacterDao().upsert(result)

    fun getSavedCharacters() = db.getCharacterDao().getAllCharacters()
}