package com.fdlr.omieteste.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fdlr.omieteste.data.model.CatalogItemEntity

@Dao
interface CatalogDao {
    @Insert
    suspend fun insertCatalogItem(item: CatalogItemEntity)

    @Query("SELECT * FROM catalog_items")
    suspend fun getAllCatalogItems(): List<CatalogItemEntity>
}