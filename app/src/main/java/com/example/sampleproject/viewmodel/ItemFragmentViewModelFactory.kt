package com.example.sampleproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sampleproject.model.ExecutorType
import com.example.sampleproject.model.Repository


/**
 * @author Mohd Haseen
 */
class ItemFragmentViewModelFactory(private val type: ExecutorType) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (type) {
            ExecutorType.RX_JAVA-> ItemFragmentViewModel(Repository()) as T
            ExecutorType.COROUTINE-> ItemFragmentViewModelWithCoroutine(Repository()) as T
        }
    }
}