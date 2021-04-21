package com.durbindevs.rickandmorty.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.durbindevs.rickandmorty.CharacterActivity
import com.durbindevs.rickandmorty.R
import com.durbindevs.rickandmorty.adapter.CharacterAdapter
import com.durbindevs.rickandmorty.databinding.FragmentAllCharactersBinding
import com.durbindevs.rickandmorty.ui.viewmodels.MainViewModel
import com.durbindevs.rickandmorty.ui.viewmodels.MainViewModel.Companion.pageNumber
import com.durbindevs.rickandmorty.utils.Constants.Companion.ERROR
import com.durbindevs.rickandmorty.utils.Constants.Companion.QUERY_PAGE_SIZE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllCharactersFragment : Fragment(R.layout.fragment_all_characters) {

    private val viewModel: MainViewModel by viewModels()
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
        setupRecycler()


        viewModel.characterResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful && response.body() != null) {
                characterAdapter.differ.submitList(response.body()!!.results.toList())

            } else {
                Log.e(ERROR, "Error fetching data")
            }
        })

ItemTouchHelper(itemTouch).attachToRecyclerView(binding.rvAllCharacters)
    }

    val itemTouch = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT
            or ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val result = characterAdapter.differ.currentList[position]
            characterAdapter.notifyItemChanged(position)
            viewModel.saveCharacter(result)
            Toast.makeText(requireContext(), "Saved to favorites!", Toast.LENGTH_SHORT).show()
        }

    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNoErrors = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNoErrors && isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                pageNumber++
                Log.d("test", "viewmodel: ${pageNumber}")
                viewModel.getAllCharacters(pageNumber.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

 private fun setupRecycler() = binding.rvAllCharacters.apply {
     adapter = characterAdapter
     layoutManager = LinearLayoutManager(requireContext())
        addItemDecoration(
            DividerItemDecoration(requireContext(),
            DividerItemDecoration.VERTICAL)
        )
     addOnScrollListener(this@AllCharactersFragment.scrollListener)
 }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}