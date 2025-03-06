package com.fdlr.omieteste.domain.usecase

import com.fdlr.omieteste.domain.model.CatalogItem
import com.fdlr.omieteste.domain.repository.CatalogRepository

class AddCatalogItemUseCase(private val repository: CatalogRepository) {
    suspend operator fun invoke(item: CatalogItem) {
        repository.addCatalogItem(item)
    }
}
