package com.fdlr.omieteste.data.datasouce.local.converters

import androidx.room.TypeConverter
import com.fdlr.omieteste.data.model.CatalogItemEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromCatalogItemList(items: List<CatalogItemEntity>?): String? {
        return Gson().toJson(items)
    }

    @TypeConverter
    fun toCatalogItemList(data: String?): List<CatalogItemEntity>? {
        if (data.isNullOrEmpty()) return emptyList()
        val listType = object : TypeToken<List<CatalogItemEntity>>() {}.type
        return Gson().fromJson(data, listType)
    }
}
