package com.diatomicsoft.wallpaperapp.model.network

import com.diatomicsoft.wallpaperapp.model.data.Category
import com.diatomicsoft.wallpaperapp.model.data.Wallpaper
import com.diatomicsoft.wallpaperapp.util.BASEURL
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("/api/category/all/1/20")
    suspend fun getCategory(): Response<List<Category>>

    @GET("/api/walpaper/allByCatId/{catId}/0/100")
    suspend fun getImagesByCategoryId(
        @Path("catId") catId: Int
    ): Response<List<Wallpaper>>



    companion object {
        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): Api {
            val okHttpClient =
                OkHttpClient.Builder().addInterceptor(networkConnectionInterceptor).build()
            return Retrofit.Builder().client(okHttpClient).baseUrl(
                BASEURL
            ).addConverterFactory(GsonConverterFactory.create()).build()
                .create(Api::class.java)
        }
    }

}