package com.fdlr.omieteste.domain.repository

import com.fdlr.omieteste.domain.model.CatalogItem

interface CatalogRepository {
    suspend fun addCatalogItem(item: CatalogItem)
    suspend fun getCatalogItems(): List<CatalogItem>
}

