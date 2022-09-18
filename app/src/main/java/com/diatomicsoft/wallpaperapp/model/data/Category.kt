package com.diatomicsoft.wallpaperapp.model.data

import android.os.Parcelable
import com.diatomicsoft.wallpaperapp.adapter.generic_adapter.ListItemViewModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val image: String,
    val walpaper_count: Int
):ListItemViewModel(), Parcelable
