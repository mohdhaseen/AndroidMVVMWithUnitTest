package com.example.sampleproject.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.sampleproject.ItemFragmentViewState
import com.example.sampleproject.model.ExecutorType
import com.example.sampleproject.model.Response
import com.example.sampleproject.view.ui.theme.SampleTheme
import com.example.sampleproject.viewmodel.ItemFragmentViewModelFactory
import com.example.sampleproject.viewmodel.ItemFragmentViewModelWithCoroutine

class MainComposeActivity : ComponentActivity() {
    private lateinit var viewModel: ItemFragmentViewModelWithCoroutine
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        setContent {
            SampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyScreen()
                }
            }
        }
    }

    private fun initViewModel() {
        val viewModelFactory = ItemFragmentViewModelFactory(ExecutorType.COROUTINE)
        viewModel = ViewModelProvider(this, viewModelFactory)[ItemFragmentViewModelWithCoroutine::class.java]
    }


    private fun handleError(throwable: Throwable) {

    }

    @Composable
    private fun loadUI(response: Response) {
        response.results?.let { CustomViews.Conversation(messages = it) }

    }

    private fun hideLoader() {

    }

    @Composable
    private fun showLoader() {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = "Loading...")
            }
        }
    }

    @Composable
    fun MyScreen() {
        val viewState by viewModel.liveData.observeAsState(initial = ItemFragmentViewState.ShowLoader)

        LaunchedEffect(true) {
            viewModel.getMostViewedArticles()
        }

        when (viewState) {
            is ItemFragmentViewState.ShowLoader -> showLoader()
            is ItemFragmentViewState.HideLoader -> hideLoader()
            is ItemFragmentViewState.LoadData -> loadUI((viewState as ItemFragmentViewState.LoadData).response)
            is ItemFragmentViewState.HandleError -> handleError((viewState as ItemFragmentViewState.HandleError).throwable)

            else -> {
                // Do nothing or handle other states if needed
            }
        }
    }
}
