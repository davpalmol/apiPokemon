package com.example.exemplepokemon

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonNameTextView: TextView
    private lateinit var pokemonIdTextView: TextView
    private lateinit var pokemonImageView: ImageView
    private lateinit var pokemonNameEditText: EditText
    private lateinit var boton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonNameTextView = findViewById(R.id.pokemonNameTextView)
        pokemonImageView = findViewById(R.id.pokemonImageView)

        pokemonNameEditText = findViewById(R.id.pokemonNameEditText)
        boton = findViewById(R.id.boton)

        boton.setOnClickListener {
            val pokemonName = pokemonNameEditText.text.toString()

            fetchPokemonData(pokemonName)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fetchPokemonData(pokemonName: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pokemonApiService = retrofit.create(PokemonApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val pokemon = pokemonApiService.getPokemon(pokemonName)

                withContext(Dispatchers.Main) {
                    pokemonNameTextView.text = pokemon.name
                    pokemonIdTextView.text = pokemon.id.toString()

                    Picasso.get()
                        .load(pokemon.sprites.frontDefault)
                        .into(pokemonImageView)
                }
            } catch (e:Exception){
                Log.e("MainActivity", "Error fetching Pokémon data: ${e.message}")
            }

        }
    }
}