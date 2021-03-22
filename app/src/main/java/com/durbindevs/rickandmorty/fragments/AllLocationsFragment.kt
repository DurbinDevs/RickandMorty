package com.durbindevs.rickandmorty.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.durbindevs.rickandmorty.CharacterActivity
import com.durbindevs.rickandmorty.R
import com.durbindevs.rickandmorty.adapter.LocationAdapter
import com.durbindevs.rickandmorty.databinding.FragmentAllLocationsBinding
import com.durbindevs.rickandmorty.ui.viewmodels.MainViewModel

class AllLocationsFragment: Fragment(R.layout.fragment_all_locations) {

    private val locationAdapter by lazy { LocationAdapter() }
    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentAllLocationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as CharacterActivity).viewModel
        setupRecycler()

        viewModel.getAllLocations()
        viewModel.locationResponse.observe(viewLifecycleOwner, Observer { response ->
            locationAdapter.differ.submitList(response.body()!!.results)
        })
    }

    private fun setupRecycler() = binding.rvAllLocations.apply {
        adapter = locationAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(
            DividerItemDecoration(requireContext(),
                DividerItemDecoration.VERTICAL)
        )
    }
}