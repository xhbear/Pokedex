package com.example.pokedex.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.databinding.FragmentPokedexHomePageBinding
import com.example.pokedex.model.PokedexAdapter
import com.example.pokedex.model.PokedexViewModel
import kotlinx.coroutines.launch

class PokedexHomePage : Fragment() {

    private var _binding: FragmentPokedexHomePageBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PokedexViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokedexHomePageBinding.inflate(inflater, container, false)
        val view = binding.root

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /**
         * setup recyclerView settings:
         * layoutManager -> GridLayout(2 columns)
         * adapter -> listAdapter
         */
        val layoutManager = GridLayoutManager(context, 2)
        binding.pokedexItemRecyclerView.layoutManager = layoutManager
        val adapter = PokedexAdapter()
        binding.pokedexItemRecyclerView.adapter = adapter

        /** notify viewModel to start OkHttp request */
        viewModel.viewModelScope.launch {
            viewModel.sendRequestWithOkHttp()
        }

        /** observe changes in pokemonList data and notify adapter subsequently */
        viewModel.pokemonList.observe(viewLifecycleOwner, Observer { newData ->
            adapter.submitList(newData)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}