package com.durbindevs.rickandmorty.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.durbindevs.rickandmorty.CharacterActivity
import com.durbindevs.rickandmorty.R
import com.durbindevs.rickandmorty.adapter.CharacterAdapter
import com.durbindevs.rickandmorty.databinding.FragmentAllCharactersBinding
import com.durbindevs.rickandmorty.ui.viewmodels.MainViewModel
import com.durbindevs.rickandmorty.utils.Constants.Companion.ERROR

class AllCharactersFragment : Fragment(R.layout.fragment_all_characters) {

    private lateinit var viewModel: MainViewModel
    private val characterAdapter by lazy { CharacterAdapter() }
    private var _binding: FragmentAllCharactersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("test", "response is : ")
        viewModel = (activity as CharacterActivity).viewModel
        setupRecycler()

        viewModel.getAllCharacters()
        viewModel._response.observe(viewLifecycleOwner, { response ->
            Log.d("test", "response is : $response")
            if (response.isSuccessful && response.body() != null) {
                Log.d("test", "response is : $response")
                characterAdapter.charList = response.body()!!.results
            } else {
                Log.d("test", "response is : $response")
                Log.e(ERROR, "Error fetching data")
            }
        })

    }

 private fun setupRecycler() = binding.rvAllCharacters.apply {
     adapter = characterAdapter
     layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(
            DividerItemDecoration(requireContext(),
            DividerItemDecoration.VERTICAL)
        )
 }

}