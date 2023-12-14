package com.example.sampleproject.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.example.sampleproject.viewmodel.MyScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainComposeActivity : ComponentActivity() {
     private val viewModel: MyScreenViewModel by viewModels()
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

    @Composable
    private fun HandleError(throwable: Throwable) {
        // show error view
    }

    @Composable
    private fun LoadUI(response: Response) {
        response.results?.let { CustomViews.Conversation(messages = it) }

    }

    @Composable
    private fun HideLoader() {
        // do nothing
    }

    @Composable
    private fun ShowLoader() {
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
            is ViewStates.ShowLoader -> ShowLoader()
            is ViewStates.HideLoader -> HideLoader()
            is ViewStates.LoadData -> LoadUI((viewState as ViewStates.LoadData).response)
            is ViewStates.HandleError -> HandleError((viewState as ViewStates.HandleError).throwable)

            else -> {
                // Do nothing or handle other states if needed
            }
        }
    }
}
