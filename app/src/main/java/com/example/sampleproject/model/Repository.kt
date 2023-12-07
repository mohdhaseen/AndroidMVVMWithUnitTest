package com.example.sampleproject.repository

import com.example.sampleproject.model.Response
import io.reactivex.Single

/**
 * @author Mohd Haseen
 *
 */
interface Repository {

    fun getMostViewedArticles(apiKey: String): Single<Response>

}