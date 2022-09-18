package com.diatomicsoft.wallpaperapp.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diatomicsoft.wallpaperapp.model.data.Category
import com.diatomicsoft.wallpaperapp.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repo: CategoryRepository) : ViewModel() {
    var categories = MutableLiveData<List<Category>>()

    init {
        viewModelScope.launch {
           categories.value =  repo.getCategories()
        }
    }
}