package com.diatomicsoft.wallpaperapp.model.network

import com.diatomicsoft.wallpaperapp.util.BASEURL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkInstance @Inject constructor(private val networkConnectionInterceptor: NetworkConnectionInterceptor) {

    fun getInstance(): Retrofit {
        val okHttpClient =
            OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor).build()
        return Retrofit.Builder().client(okHttpClient).baseUrl(
            BASEURL
        ).addConverterFactory(GsonConverterFactory.create()).build()
      }


}