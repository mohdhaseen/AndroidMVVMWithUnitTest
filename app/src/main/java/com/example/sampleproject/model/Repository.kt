package com.example.sampleproject.model

import com.example.sampleproject.model.Response
import io.reactivex.Single

/**
 * @author Mohd Haseen
 *
 */
interface Repository {

    fun getMostViewedArticles(): Single<Response>
    suspend fun getMostViewedArticlesWithCoroutine(): Response

}