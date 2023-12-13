package com.example.sampleproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleproject.ViewStates
import com.example.sampleproject.model.Repository
import com.example.sampleproject.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author Mohd Haseen
 */
class MyScreenViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val mutableLiveData = MutableLiveData<ViewStates>()
    val liveData: LiveData<ViewStates>
        get() = mutableLiveData

    fun getMostViewedArticles() {
        mutableLiveData.value = ViewStates.ShowLoader
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
        mutableLiveData.value = ViewStates.HideLoader
        mutableLiveData.value = ViewStates.LoadData(response)
    }

    private fun onError(throwable: Throwable?) {
        mutableLiveData.value = ViewStates.HideLoader
        mutableLiveData.value = throwable?.let { ViewStates.HandleError(it) }
    }

}