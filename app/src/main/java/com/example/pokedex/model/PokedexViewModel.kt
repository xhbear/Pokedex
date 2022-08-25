package com.example.pokedex.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.data.Pokemon
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class PokedexViewModel : ViewModel() {

    /** Pokemon data */
    private val _pokemonList: MutableLiveData<List<Pokemon>> by lazy {
        MutableLiveData<List<Pokemon>>()
    }
    val pokemonList: LiveData<List<Pokemon>> get() = _pokemonList

    /** fetch JSON file, translate via GSON */
    suspend fun sendRequestWithOkHttp() = withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(Constant.pokemonUrl)
                .build()
            val response = client.newCall(request).execute()
            val responseData = response.body?.string()
            if (responseData != null) {
                parseJSONWithGSON(responseData)
            }
        } catch (e:Exception) {
            e.printStackTrace()
        }
    }

    /**
     * convert JSON object to list of Pokemon instances
     * throughout the programme, fires only ONCE. */
    private fun parseJSONWithGSON(jsonData: String) {
        val gson = Gson()
        val typeOf = object : TypeToken<List<Pokemon>>() {}.type
        val pokemonList = gson.fromJson<List<Pokemon>>(jsonData, typeOf)
        if (pokemonList != null) {
            _pokemonList.postValue(pokemonList)
        }
    }
}