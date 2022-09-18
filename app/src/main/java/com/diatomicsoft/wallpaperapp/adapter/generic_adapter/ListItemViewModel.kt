package com.diatomicsoft.wallpaperapp.adapter.generic_adapter

import androidx.room.TypeConverter
import androidx.room.TypeConverters


abstract class ListItemViewModel{
    @TypeConverters
    var adapterPosition: Int = -1
    @TypeConverters
    var onListItemViewClickListener: GenericAdapter.OnListItemViewClickListener? = null
}