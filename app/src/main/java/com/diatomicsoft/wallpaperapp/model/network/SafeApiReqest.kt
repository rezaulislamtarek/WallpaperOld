package com.diatomicsoft.wallpaperapp.model.network

import com.diatomicsoft.wallpaperapp.util.ApiExceptions
import retrofit2.Response

open class SafeApiRequest() {

    suspend fun<T: Any> apiRequestObject(call: suspend ()-> Response<T>) : T{
        val response = call.invoke()
        if(response.isSuccessful) return response.body()!!
        else throw ApiExceptions(response.message()+"\nError Code "+response.code())
    }

    suspend fun<T: Any> apiRequestList(call: suspend () -> Response<List<T>>) : List<T> {
        val response = call.invoke()
        if(response.isSuccessful ) return response.body()!!
        else throw ApiExceptions(response.message()+"\nError Code "+response.code())
    }
}