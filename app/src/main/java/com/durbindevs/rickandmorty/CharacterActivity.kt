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

class CharacterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCharactorsBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository(CharacterDatabase(this))
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel  = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        val childFragment = supportFragmentManager.findFragmentByTag("nav_fragment") as? NavHostFragment
       binding.bottomNavView.setupWithNavController(childFragment!!.navController)

    }
}