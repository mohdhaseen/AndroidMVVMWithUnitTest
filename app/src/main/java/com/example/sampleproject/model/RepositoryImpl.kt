package com.example.sampleproject.repository

import com.example.sampleproject.model.Response
import com.example.sampleproject.network.ApiHandler
import io.reactivex.Single

/**
 * @author Mohd Haseen
 *
 */
class RepositoryImpl : Repository {
    private val shipmentApi = ApiHandler.API

    override fun getMostViewedArticles(apiKey: String): Single<Response> {
        return shipmentApi.getMostViewedArticles(apiKey)
    }
}