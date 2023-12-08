package com.example.sampleproject.network

import com.example.sampleproject.api.ApiService


/**
 * @author Mohd Haseen
 *
 */

class ApiHandler {

    companion object {
        val API: ApiService by lazy { RetrofitHandler.getRetrofitInstance().create(
            ApiService::class.java) }
    }
}
