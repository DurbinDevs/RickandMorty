package com.durbindevs.rickandmorty.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.durbindevs.rickandmorty.models.Result


@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(result: Result): Long

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): LiveData<List<Result>>

    @Delete
    suspend fun deleteCharacters(result: Result)
}