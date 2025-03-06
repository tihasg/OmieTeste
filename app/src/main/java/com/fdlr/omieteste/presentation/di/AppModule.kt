package com.fdlr.omieteste.presentation.di

import com.fdlr.omieteste.data.datasouce.local.AppDatabase
import com.fdlr.omieteste.data.repository.CatalogRepositoryImpl
import com.fdlr.omieteste.data.repository.OrderRepositoryImpl
import com.fdlr.omieteste.domain.repository.CatalogRepository
import com.fdlr.omieteste.domain.repository.OrderRepository
import com.fdlr.omieteste.domain.usecase.AddCatalogItemUseCase
import com.fdlr.omieteste.domain.usecase.GetTotalPriceUseCase
import com.fdlr.omieteste.domain.usecase.AddOrderUseCase
import com.fdlr.omieteste.domain.usecase.GetCatalogItemsUseCase
import com.fdlr.omieteste.domain.usecase.GetOrdersUseCase
import com.fdlr.omieteste.presentation.viewmodels.CatalogViewModel
import com.fdlr.omieteste.presentation.viewmodels.OrderHistoryViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

private val viewModelModule = module {
    viewModelOf(::OrderHistoryViewModel)
    viewModelOf(::CatalogViewModel)
}

private val repositoryModule = module {
    singleOf(::OrderRepositoryImpl) { bind<OrderRepository>() }
    singleOf(::CatalogRepositoryImpl) { bind<CatalogRepository>() }
}

private val useCaseModule = module {
    singleOf(::GetOrdersUseCase)
    singleOf(::AddOrderUseCase)
    singleOf(::GetCatalogItemsUseCase)
    singleOf(::AddCatalogItemUseCase)
    singleOf(::GetTotalPriceUseCase)
}

private val databaseModule = module {
    single { AppDatabase.getDatabase(get()) }
    single { get<AppDatabase>().orderDao() }
    single { get<AppDatabase>().catalogDao() }
}

val appModule = listOf(viewModelModule, repositoryModule, useCaseModule, databaseModule)