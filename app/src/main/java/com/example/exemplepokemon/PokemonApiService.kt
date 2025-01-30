package com.example.exemplepokemon

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApiService {
    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Pokemon

}