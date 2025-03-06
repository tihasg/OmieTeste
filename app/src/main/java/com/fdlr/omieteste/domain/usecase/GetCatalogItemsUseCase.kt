package com.fdlr.omieteste.domain.usecase

import com.fdlr.omieteste.domain.model.CatalogItem
import com.fdlr.omieteste.domain.repository.CatalogRepository

class GetCatalogItemsUseCase(private val repository: CatalogRepository) {
    suspend operator fun invoke(): List<CatalogItem> {
        return repository.getCatalogItems()
    }
}