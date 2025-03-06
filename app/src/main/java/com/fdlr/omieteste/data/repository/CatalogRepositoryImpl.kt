package com.fdlr.omieteste.data.repository

import com.fdlr.omieteste.data.dao.CatalogDao
import com.fdlr.omieteste.data.mappers.CatalogItemMapperImpl
import com.fdlr.omieteste.domain.model.CatalogItem
import com.fdlr.omieteste.domain.repository.CatalogRepository

class CatalogRepositoryImpl(private val dao: CatalogDao) : CatalogRepository {
    override suspend fun addCatalogItem(item: CatalogItem) {
        dao.insertCatalogItem(item.toEntity())
    }

    override suspend fun getCatalogItems(): List<CatalogItem> {
        return dao.getAllCatalogItems().map { CatalogItemMapperImpl().toDomain(it) }
    }
}
