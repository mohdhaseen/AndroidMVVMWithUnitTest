package com.example.sampleproject

import com.example.sampleproject.model.Response

/**
 * @author Mohd Haseen
 *
 */

sealed class ItemFragmentViewState {

    object ShowLoader : ItemFragmentViewState()

    object HideLoader : ItemFragmentViewState()

    object OnCleared : ItemFragmentViewState()

    data class LoadData(val response: Response) : ItemFragmentViewState()

    data class HandleError(val throwable: Throwable) : ItemFragmentViewState()


}