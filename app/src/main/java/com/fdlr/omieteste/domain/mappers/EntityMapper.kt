package com.fdlr.omieteste.domain.mappers

interface EntityMapper<in Model, out T> {
    fun toEntity(from: Model): T
    fun toEntity(from: List<Model>): List<T> = from.map { toEntity(it) }
}