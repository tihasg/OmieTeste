package com.fdlr.omieteste.data.mappers

import com.fdlr.omieteste.data.model.CatalogItemEntity
import com.fdlr.omieteste.domain.mappers.DomainMapper
import com.fdlr.omieteste.domain.mappers.EntityMapper
import com.fdlr.omieteste.domain.model.CatalogItem

class CatalogItemMapperImpl : DomainMapper<CatalogItemEntity, CatalogItem>,
    EntityMapper<CatalogItem, CatalogItemEntity> {

    override fun toDomain(from: CatalogItemEntity): CatalogItem {
        return CatalogItem(
            id = from.id,
            name = from.name,
            description = from.description,
            price = from.price,
            quantity = from.quantity,
            imagePath = from.imagePath
        )
    }

    override fun toEntity(from: CatalogItem): CatalogItemEntity {
        return CatalogItemEntity(
            id = from.id,
            name = from.name,
            description = from.description,
            price = from.price,
            quantity = from.quantity,
            imagePath = from.imagePath
        )
    }
}