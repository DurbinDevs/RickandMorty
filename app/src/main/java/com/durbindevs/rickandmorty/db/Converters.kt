package com.durbindevs.rickandmorty.db

import androidx.room.TypeConverter
import com.durbindevs.rickandmorty.models.Location
import com.durbindevs.rickandmorty.models.Origin
import com.google.gson.Gson

class Converters {


    @TypeConverter
    fun fromLocation(location: Location): String {
        return location.name
    }

    @TypeConverter
    fun toLocation(name: String): Location {
        return Location(name, name)
    }

    @TypeConverter
    fun fromOrigin(origin: Origin): String {
        return origin.name
    }

    @TypeConverter
    fun toOrigin(name: String): Origin {
        return Origin(name, name)
    }

    @TypeConverter
    fun fromEpisode(episode: List<String>) = Gson().toJson(episode)


    @TypeConverter
    fun toEpisode(episode: String) = Gson().fromJson(episode, Array<String>::class.java).asList()

}


