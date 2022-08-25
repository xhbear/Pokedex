
package com.example.pokedex.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pokedex.data.Pokemon
import com.example.pokedex.databinding.ActivityDetailedPokedexEntryBinding
import com.google.gson.Gson

class DetailedPokedexEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailedPokedexEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailedPokedexEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gson = Gson()
        val pokemon = gson.fromJson<Pokemon>(intent.getStringExtra("pokemon"), Pokemon::class.java)
        binding.pokemonName.text = pokemon?.name
        binding.pokemonId.text = pokemon?.id
        Glide.with(binding.root.context)
            .load(pokemon?.imageurl)
            .into(binding.pokemonImage)
        binding.pokemonType.text = pokemon?.typeofpokemon.toString()
        binding.pokemonHeight.text = pokemon?.height
        binding.pokemonWeight.text = pokemon?.weight
        binding.pokemonDescription.text = pokemon?.xdescription
    }

    companion object {
        /** individual pokedex entry launch method */
        fun actionStart(context: Context, pokemon: Pokemon) {
            val gson = Gson()
            val intent = Intent(context, DetailedPokedexEntryActivity::class.java)
            intent.putExtra("pokemon", gson.toJson(pokemon))
            context.startActivity(intent)
        }
    }
}