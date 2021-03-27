package com.durbindevs.rickandmorty.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
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
        viewModel = (activity as CharacterActivity).viewModel
        setupRecycler()



      //  viewModel.getAllCharacters(pageNumber.toString())
        viewModel.characterResponse.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful && response.body() != null) {
                characterAdapter.differ.submitList(response.body()!!.results.toList())
            } else {
                Log.e(ERROR, "Error fetching data")
            }
        })

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

//    var isScrolling = false
//
//    val scrollListener = object: RecyclerView.OnScrollListener(){
//        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            super.onScrollStateChanged(recyclerView, newState)
//            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                isScrolling = true
//            }
//        }
//
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//            val lastItem = layoutManager.findLastVisibleItemPosition()
//            val totalItems = layoutManager.itemCount
//
//            if (lastItem + 2 == totalItems && isScrolling) {
//                Log.d("test", "page :$pageNumber")
//                viewModel.getAllCharacters(pageNumber.toString())
//                Log.d("test", "last item :$lastItem")
//                Log.d("test", "total item :$totalItems")
//            }
//
//        }
//    }

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