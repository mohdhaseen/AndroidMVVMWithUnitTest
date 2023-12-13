package com.example.sampleproject.model

import com.example.sampleproject.BuildConfig
import com.example.sampleproject.network.ApiHandler
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Mohd Haseen
 *
 */
class Repository @Inject constructor() {
    private val shipmentApi = ApiHandler.API

     fun getMostViewedArticles(): Single<Response> {
        return shipmentApi.getMostViewedArticles(BuildConfig.API_KEY)
    }

     suspend fun getMostViewedArticlesWithCoroutine(): Response {
        return shipmentApi.getMostViewedArticlesWithCoroutine(BuildConfig.API_KEY)
    }
}