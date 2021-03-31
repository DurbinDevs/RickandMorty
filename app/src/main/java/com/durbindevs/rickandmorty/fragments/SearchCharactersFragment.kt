package com.durbindevs.rickandmorty.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.durbindevs.rickandmorty.CharacterActivity
import com.durbindevs.rickandmorty.R
import com.durbindevs.rickandmorty.adapter.CharacterAdapter
import com.durbindevs.rickandmorty.databinding.FragmentSearchCharactersBinding
import com.durbindevs.rickandmorty.ui.viewmodels.MainViewModel
import com.durbindevs.rickandmorty.utils.Constants.Companion.ERROR

class SearchCharactersFragment : Fragment(R.layout.fragment_search_characters) {

    private lateinit var viewModel: MainViewModel
    private val characterAdapter by lazy { CharacterAdapter() }
    private var _binding: FragmentSearchCharactersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CharacterActivity).viewModel
        setupRecycler()

        binding.etSearch.addTextChangedListener { search ->
            viewModel.searchCharacters(search.toString())
        }

        viewModel.characterSearch.observe(viewLifecycleOwner, Observer { response ->
            if (response.isSuccessful && response.body() != null){
                characterAdapter.differ.submitList(response.body()!!.results)
            }else {
                Log.e(ERROR, "An error occurred")
            }
        })

    }

   // val itemTouchCallBack = object : ItemTouchHelper

    private fun setupRecycler() = binding.rvSearch.apply {
        adapter = characterAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }
}