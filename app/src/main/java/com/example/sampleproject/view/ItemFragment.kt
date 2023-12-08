package com.example.sampleproject.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.aranoah.healthkart.plus.pharmacy.shipmentv2.ItemFragmentViewModelFactory
import com.example.sampleproject.ItemFragmentViewState
import com.example.sampleproject.MyItemRecyclerViewAdapter
import com.example.sampleproject.databinding.FragmentItemListBinding
import com.example.sampleproject.model.Response
import com.example.sampleproject.viewmodel.ItemFragmentViewModel

/**
 * @author Mohd Haseen
 *
 */
class ItemFragment : Fragment() {

    companion object {
        fun getInstance(): ItemFragment {
            return ItemFragment()
        }
    }

    private lateinit var viewModel: ItemFragmentViewModel
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
        Log.d("haseen", throwable.toString())
        // handle error
    }

    private fun loadUI(response: Response) {
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            adapter = response.results?.let { MyItemRecyclerViewAdapter(it) }
        }

    }

    private fun initViewModel() {
        val viewModelFactory = ItemFragmentViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory)[ItemFragmentViewModel::class.java]
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