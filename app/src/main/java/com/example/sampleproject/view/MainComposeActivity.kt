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
import com.example.sampleproject.ViewStates
import com.example.sampleproject.model.Response
import com.example.sampleproject.view.ui.theme.SampleTheme
import com.example.sampleproject.viewmodel.ItemFragmentViewModelWithCoroutine
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainComposeActivity : ComponentActivity() {
    @Inject
    lateinit var viewModel: ItemFragmentViewModelWithCoroutine
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun handleError(throwable: Throwable) {
        // show error view
    }

    @Composable
    private fun loadUI(response: Response) {
        response.results?.let { CustomViews.Conversation(messages = it) }

    }

    private fun hideLoader() {
        // do nothing
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
        val viewState by viewModel.liveData.observeAsState(initial = ViewStates.ShowLoader)

        LaunchedEffect(true) {
            viewModel.getMostViewedArticles()
        }

        when (viewState) {
            is ViewStates.ShowLoader -> showLoader()
            is ViewStates.HideLoader -> hideLoader()
            is ViewStates.LoadData -> loadUI((viewState as ViewStates.LoadData).response)
            is ViewStates.HandleError -> handleError((viewState as ViewStates.HandleError).throwable)

            else -> {
                // Do nothing or handle other states if needed
            }
        }
    }
}
