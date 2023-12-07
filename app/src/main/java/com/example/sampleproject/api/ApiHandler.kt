package com.example.sampleproject.network


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
