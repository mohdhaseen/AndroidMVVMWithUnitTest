package com.example.sampleproject.viewmodel

import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleproject.ItemFragmentViewState
import com.example.sampleproject.model.Response
import com.example.sampleproject.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author Mohd Haseen
 */
class ItemFragmentViewModel(private val repository: Repository) : ViewModel() {

    public val disposables = CompositeDisposable()
    private val mutableLiveData = MutableLiveData<ItemFragmentViewState>()
    val liveData: LiveData<ItemFragmentViewState>
        get() = mutableLiveData

    fun getMostViewedArticles(apiKey: String) {
        mutableLiveData.value = ItemFragmentViewState.ShowLoader
        callApi(apiKey)
    }

    private fun callApi(apiKey: String) {
        disposables.add(
            repository.getMostViewedArticles(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError)
        )
    }

    private fun onSuccess(response: Response) {
        mutableLiveData.value = ItemFragmentViewState.HideLoader
        mutableLiveData.value = ItemFragmentViewState.LoadData(response)
    }

    private fun onError(throwable: Throwable) {
        mutableLiveData.value = ItemFragmentViewState.HideLoader
        mutableLiveData.value = ItemFragmentViewState.HandleError(throwable)
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    public override fun onCleared() {
        super.onCleared()
        disposables.clear()
        mutableLiveData.value = ItemFragmentViewState.OnCleared
    }
}