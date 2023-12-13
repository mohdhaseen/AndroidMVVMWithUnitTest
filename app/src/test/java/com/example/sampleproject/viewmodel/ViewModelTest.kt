package com.example.sampleproject.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.sampleproject.ViewStates
import com.example.sampleproject.MainCoroutineRule
import com.example.sampleproject.model.Repository
import com.example.sampleproject.model.Response
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ViewModelTest {

    @InjectMockKs
    private lateinit var viewModel: ItemFragmentViewModelWithCoroutine

    @RelaxedMockK
    lateinit var stateObserver: Observer<ViewStates>

    @RelaxedMockK
    lateinit var repository: Repository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun before() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel.liveData.observeForever (stateObserver)
    }

    @Test
    fun loadDataWithSuccess() {
        val response = Response(status = "success")
        coEvery { repository.getMostViewedArticlesWithCoroutine() } returns response
        viewModel.getMostViewedArticles()
        verify { stateObserver.onChanged(ViewStates.ShowLoader)}
        verify { stateObserver.onChanged(ViewStates.HideLoader)}
        Assert.assertEquals(viewModel.liveData.value, ViewStates.LoadData(response))
    }

    @Test
    fun loadDataWithError() {
        val exception = Exception()
        coEvery { repository.getMostViewedArticlesWithCoroutine() }.throws(exception)
        viewModel.getMostViewedArticles()
        verify { stateObserver.onChanged(ViewStates.ShowLoader)}
        verify { stateObserver.onChanged(ViewStates.HideLoader)}
        Assert.assertEquals(viewModel.liveData.value, ViewStates.HandleError(exception))
    }

}
