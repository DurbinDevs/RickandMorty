package com.durbindevs.rickandmorty.locationModels

data class Locations(
    val info: Info,
    val results: MutableList<Result>
)