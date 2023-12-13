package com.example.sampleproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.sampleproject.ItemFragmentViewState
import com.example.sampleproject.RxSchedulersOverrideRule
import com.example.sampleproject.model.Response
import com.example.sampleproject.model.Repository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ItemFragmentViewModelTest {

    @InjectMockKs
    private lateinit var viewModel: ItemFragmentViewModel

    @RelaxedMockK
    lateinit var stateObserver: Observer<ItemFragmentViewState>

    @RelaxedMockK
    lateinit var repository: Repository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Before
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel.liveData.observeForever (stateObserver)
    }

    @Test
    fun loadDataWithSuccess() {
        val response = Response(status = "success")
        every { repository.getMostViewedArticles() } returns Single.just(response)
        viewModel.getMostViewedArticles()
        verify { stateObserver.onChanged(ItemFragmentViewState.ShowLoader)}
        verify { stateObserver.onChanged(ItemFragmentViewState.HideLoader)}
        Assert.assertEquals(viewModel.liveData.value, ItemFragmentViewState.LoadData(response))
    }

    @Test
    fun loadDataWithError() {
        val exception = Exception()
        every { repository.getMostViewedArticles() } returns Single.error(exception)
        viewModel.getMostViewedArticles()
        verify { stateObserver.onChanged(ItemFragmentViewState.ShowLoader)}
        verify { stateObserver.onChanged(ItemFragmentViewState.HideLoader)}
        Assert.assertEquals(viewModel.liveData.value, ItemFragmentViewState.HandleError(exception))
    }

    @Test
    fun testOnClear(){
        viewModel.onCleared()
        verify { stateObserver.onChanged(ItemFragmentViewState.OnCleared) }
    }

}