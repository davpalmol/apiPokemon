package com.example.exemplepokemon

import com.google.gson.annotations.SerializedName

data class Pokemon(
    val name: String,
    val sprites: Sprites
)

data class Sprites(
    @SerializedName("front_default") val frontDefault: String
)
