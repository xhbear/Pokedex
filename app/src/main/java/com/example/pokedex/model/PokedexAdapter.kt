package com.example.pokedex.model

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.data.Pokemon
import com.example.pokedex.databinding.IndividualPokemonCardBinding
import com.example.pokedex.ui.DetailedPokedexEntryActivity

/**
 * description: recycler view adapter that handles the grid display of Pokemon entries
 * source: input source is from a fixed json document obtained from an API
 * expected behaviour: it should not inflate pokemon data beyond the single input source
 */
class PokedexAdapter :
    ListAdapter<Pokemon, PokedexAdapter.ViewHolder>(PokemonDiffCallback) {

    class ViewHolder(binding: IndividualPokemonCardBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val pokemonName: TextView = binding.pokemonName
        val pokemonId: TextView = binding.pokemonId
        val pokemonImage: ImageView = binding.pokemonImg
        val pokemonAttack: TextView = binding.pokemonAtk
        val pokemonDefence: TextView = binding.pokemonDef
        val pokemonSpeed: TextView = binding.pokemonSpd
        val favorite: ImageButton = binding.favouriteButton
        val context: Context = binding.root.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = IndividualPokemonCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)

        // react to user clicks with the favourite button
        viewHolder.favorite.setOnClickListener {
            val position = viewHolder.adapterPosition
            val pokemon = getItem(position)

            /*
             * held off from using livedata for the isFavorite property
             * as there might be null pointer issues
             */
            when (pokemon.isFavorite) {
                true -> {
                    pokemon.isFavorite = false
                    binding.favouriteButton.setImageResource(R.drawable.ic_favorite_false)
                }
                else -> {
                    pokemon.isFavorite = true
                    binding.favouriteButton.setImageResource(R.drawable.ic_favorite_true)
                }
            }
        }

        // react to user clicks to the pokemon image (opens up detailed pokedex entry)

        viewHolder.pokemonImage.setOnClickListener {
            val position = viewHolder.adapterPosition
            DetailedPokedexEntryActivity.actionStart(it.context, getItem(position))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = getItem(position)
        val image = pokemon.imageurl
        holder.pokemonName.text = pokemon.name
        holder.pokemonId.text = pokemon.id
        holder.pokemonAttack.text = "ATK ${pokemon.attack}"
        holder.pokemonDefence.text = "DEF ${pokemon.defense}"
        holder.pokemonSpeed.text = "SPD ${pokemon.speed}"
        Glide.with(holder.context)
            .load(image)
            .into(holder.pokemonImage)
        setPokemonColor(holder, pokemon.typeofpokemon[0])
        when (pokemon.isFavorite) {
            true -> {
                holder.favorite.setImageResource(R.drawable.ic_favorite_true)
            }
            else -> {
                holder.favorite.setImageResource(R.drawable.ic_favorite_false)
            }
        }
    }

    object PokemonDiffCallback: DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem.isFavorite == newItem.isFavorite
        }

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        /** set background color based on pokemon primary type */
        fun setPokemonColor(holder: ViewHolder, pokemonColor: String) {
            when (pokemonColor) {
                "Normal" -> holder.itemView.setBackgroundResource(R.color.Normal)
                "Fire" -> holder.itemView.setBackgroundResource(R.color.Fire)
                "Water" -> holder.itemView.setBackgroundResource(R.color.Water)
                "Electric" -> holder.itemView.setBackgroundResource(R.color.Electric)
                "Grass" -> holder.itemView.setBackgroundResource(R.color.Grass)
                "Ice" -> holder.itemView.setBackgroundResource(R.color.Ice)
                "Fighting" -> holder.itemView.setBackgroundResource(R.color.Fighting)
                "Poison" -> holder.itemView.setBackgroundResource(R.color.Poison)
                "Ground" -> holder.itemView.setBackgroundResource(R.color.Ground)
                "Flying" -> holder.itemView.setBackgroundResource(R.color.Flying)
                "Psychic" -> holder.itemView.setBackgroundResource(R.color.Psychic)
                "Bug" -> holder.itemView.setBackgroundResource(R.color.Bug)
                "Rock" -> holder.itemView.setBackgroundResource(R.color.Rock)
                "Ghost" -> holder.itemView.setBackgroundResource(R.color.Ghost)
                "Dragon" -> holder.itemView.setBackgroundResource(R.color.Dragon)
                "Dark" -> holder.itemView.setBackgroundResource(R.color.Dark)
                "Steel" -> holder.itemView.setBackgroundResource(R.color.Steel)
                "Fairy" -> holder.itemView.setBackgroundResource(R.color.Fairy)
            }
        }
    }

}