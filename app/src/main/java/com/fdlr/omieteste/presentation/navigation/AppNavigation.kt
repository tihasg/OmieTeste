package com.fdlr.omieteste.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fdlr.omieteste.presentation.ui.catalog.CatalogScreen
import com.fdlr.omieteste.presentation.ui.home.OrdersScreen
import com.fdlr.omieteste.presentation.viewmodels.OrderHistoryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
    isDarkMode: Boolean,
    orderViewModel: OrderHistoryViewModel = koinViewModel(),
    onDarkModeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = OrderHistory,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable<OrderHistory> {
            OrdersScreen(
                viewModel = orderViewModel,
                onNavigateToCatalog = { navController.navigate(Catalog) },
                isDarkMode = isDarkMode,
                onDarkModeChange = onDarkModeChange
            )
        }
        composable<Catalog> {
            CatalogScreen(navController = navController)
        }
    }
}

