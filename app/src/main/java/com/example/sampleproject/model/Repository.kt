package com.example.sampleproject.model

import com.example.sampleproject.BuildConfig
import com.example.sampleproject.api.ApiService
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Mohd Haseen
 *
 */
class Repository @Inject constructor(private val apiService: ApiService) {

     fun getMostViewedArticles(): Single<Response> {
        return apiService.getMostViewedArticles(BuildConfig.API_KEY)
    }

     suspend fun getMostViewedArticlesWithCoroutine(): Response {
        return apiService.getMostViewedArticlesWithCoroutine(BuildConfig.API_KEY)
    }
}