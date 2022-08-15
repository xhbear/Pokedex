package com.example.pokedex.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.pokedex.R
import com.example.pokedex.databinding.ActivityTransitionPageBinding

class TransitionPage : AppCompatActivity() {

    private lateinit var binding: ActivityTransitionPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransitionPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(PokedexHomePage())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }

    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, TransitionPage::class.java)
            context.startActivity(intent)
        }
    }
}