package com.durbindevs.rickandmorty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.durbindevs.rickandmorty.databinding.ActivityCharactorsBinding
import com.durbindevs.rickandmorty.db.CharacterDatabase
import com.durbindevs.rickandmorty.repository.Repository
import com.durbindevs.rickandmorty.ui.viewmodels.MainViewModel
import com.durbindevs.rickandmorty.ui.viewmodels.MainViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding = ActivityCharactorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val childFragment = supportFragmentManager.findFragmentByTag("nav_fragment") as? NavHostFragment
       binding.bottomNavView.setupWithNavController(childFragment!!.navController)

    }
}