package com.durbindevs.rickandmorty.models

data class Characters(
    val info: Info,
    val results: MutableList<Result>
)