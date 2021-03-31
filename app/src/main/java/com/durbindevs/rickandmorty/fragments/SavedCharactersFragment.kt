package com.durbindevs.rickandmorty.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.durbindevs.rickandmorty.CharacterActivity
import com.durbindevs.rickandmorty.R
import com.durbindevs.rickandmorty.adapter.CharacterAdapter
import com.durbindevs.rickandmorty.databinding.FragmentSavedCharactersBinding
import com.durbindevs.rickandmorty.ui.viewmodels.MainViewModel

class SavedCharactersFragment: Fragment(R.layout.fragment_saved_characters) {

    private lateinit var viewModel: MainViewModel
    private val charAdapter by lazy { CharacterAdapter() }
    private var _binding: FragmentSavedCharactersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CharacterActivity).viewModel
        setupRecycler()


        viewModel.getSavedCharacters().observe(viewLifecycleOwner, Observer { result ->
            charAdapter.differ.submitList(result)
            Log.d("test", "save observe${result}")
        })



    }

    private fun setupRecycler() = binding.rvSavedCharacters.apply {
        adapter = charAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(
            DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
