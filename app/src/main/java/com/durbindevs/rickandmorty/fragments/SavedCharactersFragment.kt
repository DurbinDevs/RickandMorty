package com.durbindevs.rickandmorty.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.durbindevs.rickandmorty.CharacterActivity
import com.durbindevs.rickandmorty.R
import com.durbindevs.rickandmorty.adapter.CharacterAdapter
import com.durbindevs.rickandmorty.databinding.FragmentSavedCharactersBinding
import com.durbindevs.rickandmorty.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedCharactersFragment: Fragment(R.layout.fragment_saved_characters) {

    private val viewModel: MainViewModel by viewModels()
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
        setupRecycler()

        ItemTouchHelper(itemCallTouchCallback).attachToRecyclerView(binding.rvSavedCharacters)

        viewModel.getSavedCharacters().observe(viewLifecycleOwner, Observer { result ->
            charAdapter.differ.submitList(result)
        })

    }

    val itemCallTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or
            ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val result = charAdapter.differ.currentList[position]
            viewModel.deleteCharacters(result)
            Toast.makeText(requireContext(), "Successfully deleted!", Toast.LENGTH_SHORT).show()
        }
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
