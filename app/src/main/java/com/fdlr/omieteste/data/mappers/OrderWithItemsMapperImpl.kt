package com.fdlr.omieteste.data.mappers

import com.fdlr.omieteste.data.model.CatalogItemEntity
import com.fdlr.omieteste.data.model.OrderEntity
import com.fdlr.omieteste.domain.mappers.DomainMapper
import com.fdlr.omieteste.domain.mappers.EntityMapper
import com.fdlr.omieteste.domain.model.OrderItemDetail
import com.fdlr.omieteste.domain.model.OrderWithItems

class OrderWithItemsMapperImpl : DomainMapper<OrderEntity, OrderWithItems>,
    EntityMapper<OrderWithItems, OrderEntity> {

    override fun toDomain(from: OrderEntity): OrderWithItems {
        return OrderWithItems(
            id = from.id,
            totalPrice = from.totalPrice,
            date = from.date,
            customerName = from.customerName,
            items = from.items.map {
                OrderItemDetail(
                    name = it.name,
                    quantity = it.quantity,
                    price = it.price,
                    imagePath = it.imagePath
                )
            }
        )
    }

    override fun toEntity(from: OrderWithItems): OrderEntity {
        return OrderEntity(
            id = from.id,
            totalPrice = from.totalPrice,
            date = from.date,
            customerName = from.customerName,
            items = from.items.map {
                CatalogItemEntity(
                    name = it.name,
                    quantity = it.quantity,
                    price = it.price,
                    imagePath = it.imagePath
                )
            }
        )
    }
}
