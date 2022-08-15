package com.example.pokedex.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.Pokemon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

object PokedexViewModel : ViewModel() {

    /** Pokemon data */
    private val _pokemonList: MutableLiveData<List<Pokemon>> by lazy {
        MutableLiveData<List<Pokemon>>()
    }
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    /* filter logic */

    /* sort logic */

    /** fetch JSON file, translate via GSON */
    fun sendRequestWithOkHttp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://gist.githubusercontent.com/mrcsxsiq/b94dbe9ab67147b642baa9109ce16e44/raw/97811a5df2df7304b5bc4fbb9ee018a0339b8a38")
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    viewModelScope.launch(Dispatchers.Main) {
                        parseJSONWithGSON(responseData)
                    }
                }
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }

    /** convert JSON object to Pokemon instance */
    private fun parseJSONWithGSON(jsonData: String) {
        val gson = Gson()
        val typeOf = object : TypeToken<List<Pokemon>>() {}.type
        val pokemonList = gson.fromJson<List<Pokemon>>(jsonData, typeOf)
        if (pokemonList != null) {
            _pokemonList.value = pokemonList
        }
    }
}