package com.diatomicsoft.wallpaperapp.repository

import com.diatomicsoft.wallpaperapp.model.data.Category
import com.diatomicsoft.wallpaperapp.model.network.Api
import com.diatomicsoft.wallpaperapp.model.network.SafeApiRequest
import javax.inject.Inject

class CategoryRepository @Inject constructor( val api: Api) : SafeApiRequest()  {
    suspend fun getCategories(): List<Category>{
        return apiRequestList{api.getCategory()}
    }
}