package com.durbindevs.rickandmorty.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.durbindevs.rickandmorty.models.Result


@Database(
    entities = [Result::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CharacterDatabase: RoomDatabase() {

    abstract fun getCharacterDao(): CharacterDao

}