package com.durbindevs.rickandmorty.api

import com.durbindevs.rickandmorty.locationModels.Locations
import com.durbindevs.rickandmorty.models.Characters
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("character")
    suspend fun getAllCharacters() : Response<Characters>

    @GET("location")
    suspend fun getAllLocations() : Response<Locations>

    @GET("character")
    suspend fun searchCharacters(
        @Query("name")
        search: String
    ) : Response<Characters>
}