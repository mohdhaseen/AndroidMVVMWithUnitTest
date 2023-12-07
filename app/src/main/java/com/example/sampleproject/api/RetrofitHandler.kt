package com.example.sampleproject.network

import android.content.Context
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference

/**
 * @author Mohd Haseen
 *
 */
class RetrofitHandler {

    companion object {
        private const val BASE_URL = "https://api.nytimes.com/"
        private lateinit var contextWeakReference: WeakReference<Context>

        @JvmStatic
        fun init(context: Context) {
            contextWeakReference = WeakReference(context)
        }

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        private fun getContext() = contextWeakReference.get()
    }
}