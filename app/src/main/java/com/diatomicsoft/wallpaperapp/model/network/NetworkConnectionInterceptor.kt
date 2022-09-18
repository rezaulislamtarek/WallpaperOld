package com.diatomicsoft.wallpaperapp.model.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.diatomicsoft.wallpaperapp.util.NoInternetExceptions
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor (@ApplicationContext context: Context) :Interceptor {
    private val appContext = context

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) throw NoInternetExceptions("Make sure you have a active data connection")
        val newRequest: Request = chain.request().newBuilder()
            //.addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

    /*
     fun getInstance(): OkHttpClient {
        val build = OkHttpClient.Builder()
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES).addInterceptor { chain: Interceptor.Chain ->
                val original = chain.request()
                val requestBuilder: Request.Builder =
                    original
                        .newBuilder()
                        //.header("Authorization", "Bearer " + sharedPreferences.getTk())
                        .method(original.method(), original.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        build.addInterceptor(httpLoggingInterceptor)

        return build.build()
    }
    * */
}