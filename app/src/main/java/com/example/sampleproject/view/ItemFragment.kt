package com.example.sampleproject.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sampleproject.viewmodel.ItemFragmentViewModelFactory
import com.example.sampleproject.ItemFragmentViewState
import com.example.sampleproject.MyItemRecyclerViewAdapter
import com.example.sampleproject.databinding.FragmentItemListBinding
import com.example.sampleproject.model.ExecutorType
import com.example.sampleproject.model.Response
import com.example.sampleproject.viewmodel.ItemFragmentViewModelWithCoroutine

/**
 * @author Mohd Haseen
 *
 */
class ItemFragment : Fragment() {

    companion object {
        fun getInstance(): ItemFragment {
            return ItemFragment()
        }
        const val TAG = "ItemFragment"
    }

    private lateinit var viewModel: ItemFragmentViewModelWithCoroutine
    private lateinit var binding: FragmentItemListBinding

    private fun addObserver() {
        viewModel.liveData.observe(viewLifecycleOwner) {
            when (it) {
                is ItemFragmentViewState.ShowLoader -> showLoader()
                is ItemFragmentViewState.HideLoader -> hideLoader()
                is ItemFragmentViewState.LoadData -> loadUI(it.response)
                is ItemFragmentViewState.HandleError -> handleError(it.throwable)

                else -> {
                    // Do nothing
                }
            }
        }
    }

    private fun hideLoader() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoader() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun handleError(throwable: Throwable) {
        Log.d(TAG, throwable.toString())
    }

    private fun loadUI(response: Response) {
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = response.results?.let { MyItemRecyclerViewAdapter(it) }
        }

    }

    private fun initViewModel() {
        val viewModelFactory = ItemFragmentViewModelFactory(ExecutorType.COROUTINE)
        viewModel = ViewModelProvider(this, viewModelFactory)[ItemFragmentViewModelWithCoroutine::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        addObserver()
        viewModel.getMostViewedArticles()
    }

}