package com.durbindevs.rickandmorty.di

import android.app.Application
import androidx.room.Room
import com.durbindevs.rickandmorty.api.CharacterApi
import com.durbindevs.rickandmorty.db.CharacterDatabase
import com.durbindevs.rickandmorty.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideCharacterApi(retrofit: Retrofit): CharacterApi =
        retrofit.create(CharacterApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CharacterDatabase =
        Room.databaseBuilder(app, CharacterDatabase::class.java, "character_database")
            .build()
}