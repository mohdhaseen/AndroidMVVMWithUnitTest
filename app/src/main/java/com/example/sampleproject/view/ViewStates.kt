package com.example.sampleproject

import com.example.sampleproject.model.Response

/**
 * @author Mohd Haseen
 *
 */

sealed class ViewStates {

    object ShowLoader : ViewStates()

    object HideLoader : ViewStates()

    object OnCleared : ViewStates()

    data class LoadData(val response: Response) : ViewStates()

    data class HandleError(val throwable: Throwable) : ViewStates()


}