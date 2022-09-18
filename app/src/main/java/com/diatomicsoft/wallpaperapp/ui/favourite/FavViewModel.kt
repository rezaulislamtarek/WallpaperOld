package com.diatomicsoft.wallpaperapp.ui.favourite

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.wallpaperapp.model.data.Wallpaper
import com.diatomicsoft.wallpaperapp.repository.FavRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(repo: FavRepository, @ApplicationContext context: Context) : ViewModel() {
    var favData = MutableLiveData<List<Wallpaper>>()
    init {
        viewModelScope.launch(Dispatchers.IO){
            val favTmpData: List<Wallpaper> = repo.getWallpapers()
            withContext(Dispatchers.Main){
                favData.value = favTmpData
            }
        }
    }
}