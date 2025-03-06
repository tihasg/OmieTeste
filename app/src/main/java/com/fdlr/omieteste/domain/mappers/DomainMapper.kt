package com.fdlr.omieteste.domain.mappers

interface DomainMapper<in T, out Model> {
    fun toDomain(from: T): Model
    fun toDomain(from: List<T>): List<Model> = from.map { it -> toDomain(it) }
}
