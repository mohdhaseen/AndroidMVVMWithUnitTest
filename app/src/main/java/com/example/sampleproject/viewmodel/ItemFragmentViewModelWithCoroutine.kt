package com.example.sampleproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleproject.ItemFragmentViewState
import com.example.sampleproject.model.Response
import com.example.sampleproject.model.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Mohd Haseen
 */
class ItemFragmentViewModelWithCoroutine(private val repository: Repository) : ViewModel() {

    private val mutableLiveData = MutableLiveData<ItemFragmentViewState>()
    val liveData: LiveData<ItemFragmentViewState>
        get() = mutableLiveData

    fun getMostViewedArticles() {
        mutableLiveData.value = ItemFragmentViewState.ShowLoader
        callApi()
    }

    private fun callApi() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.getMostViewedArticlesWithCoroutine()
                withContext(Dispatchers.Main) {
                    onSuccess(result)
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                onError(ex)}
            }
        }
    }

    private fun onSuccess(response: Response) {
        mutableLiveData.value = ItemFragmentViewState.HideLoader
        mutableLiveData.value = ItemFragmentViewState.LoadData(response)
    }

    private fun onError(throwable: Throwable?) {
        mutableLiveData.value = ItemFragmentViewState.HideLoader
        mutableLiveData.value = throwable?.let { ItemFragmentViewState.HandleError(it) }
    }

}