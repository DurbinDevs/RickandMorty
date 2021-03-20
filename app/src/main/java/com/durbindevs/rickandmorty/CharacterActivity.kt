package com.durbindevs.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.durbindevs.rickandmorty.databinding.ActivityCharactorsBinding

class CharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharactorsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val childFragment = supportFragmentManager.findFragmentByTag("nav_fragment") as? NavHostFragment
       binding.bottomNavView.setupWithNavController(childFragment!!.navController)

    }
}