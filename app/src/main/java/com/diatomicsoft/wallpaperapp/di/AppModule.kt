package com.diatomicsoft.wallpaperapp.di

import android.content.Context
import com.diatomicsoft.wallpaperapp.model.db.AppDatabase
import com.diatomicsoft.wallpaperapp.model.network.Api
import com.diatomicsoft.wallpaperapp.model.network.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApi(networkConnectionInterceptor: NetworkConnectionInterceptor):Api{
        return Api.invoke(networkConnectionInterceptor)
    }

    @Provides
    fun provideDb(@ApplicationContext context: Context) : AppDatabase{
        return AppDatabase.buildDataBase(context)
    }

    @Provides
    fun provideWallpaperDao(db: AppDatabase) = db.wallPaperDao()
}