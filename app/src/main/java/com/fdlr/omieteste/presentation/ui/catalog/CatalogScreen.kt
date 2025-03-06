package com.fdlr.omieteste.presentation.ui.catalog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fdlr.omieteste.R
import com.fdlr.omieteste.domain.model.CartItem
import com.fdlr.omieteste.presentation.intent.CatalogIntent
import com.fdlr.omieteste.presentation.navigation.OrderHistory
import com.fdlr.omieteste.presentation.state.CatalogState
import com.fdlr.omieteste.presentation.ui.catalog.components.BottomSheetWithTextField
import com.fdlr.omieteste.presentation.ui.catalog.components.CatalogListItem
import com.fdlr.omieteste.presentation.ui.catalog.registration.AddCatalogItemDialog
import com.fdlr.omieteste.presentation.ui.common.LoadingScreen
import com.fdlr.omieteste.presentation.utils.toBrazilianCurrency
import com.fdlr.omieteste.presentation.viewmodels.CatalogViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: CatalogViewModel = koinViewModel()
) {
    val state by viewModel.catalogState.collectAsState()
    var totalAndQtd by remember { mutableStateOf(Pair(0.0, 0)) }
    var createItemDialogVisibility by remember { mutableStateOf(false) }
    var catalogItems by remember { mutableStateOf<List<CartItem>>(emptyList()) }
    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.catalog_title)) },
                actions = {
                    IconButton(onClick = { createItemDialogVisibility = true }) {
                        Icon(Icons.Default.AddCircle, contentDescription = "Adicionar Item")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        bottomBar = {
            AnimatedVisibility(
                visible = totalAndQtd.second > 0,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut()
            ) {
                BottomAppBar {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                stringResource(R.string.total_order),
                                style = MaterialTheme.typography.labelSmall
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    totalAndQtd.first.toBrazilianCurrency(),
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )

                                val itemText = pluralStringResource(
                                    id = R.plurals.item_count,
                                    count = totalAndQtd.second,
                                    totalAndQtd.second
                                )

                                Text(
                                    text = " / $itemText",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                showBottomSheet = true
                            },
                        ) {
                            Text(stringResource(R.string.btt_continue))
                        }
                    }
                }
            }
        }
    ) { padding ->
        when (val currentState = state) {
            is CatalogState.Loading -> LoadingScreen()

            is CatalogState.Error -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    Text(
                        currentState.message,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            is CatalogState.CartAndTotalItems -> {
                totalAndQtd = Pair(currentState.totalValue, currentState.totalItems)
                catalogItems = currentState.cartItems
            }

            is CatalogState.NavigateToOrderHistory -> {
                LaunchedEffect(Unit) {
                    navController.navigate(OrderHistory) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            if (catalogItems.isEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.catalog_empty_list),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                itemsIndexed(catalogItems) { index, item ->
                    CatalogListItem(
                        item = item,
                        intent = viewModel::processIntent,
                        index = index,
                        catalogItems = catalogItems
                    )
                }
            }
        }

        BottomSheetWithTextField(
            showBottomSheet = showBottomSheet,
            onDismissRequest = { clientName ->
                showBottomSheet = false
                clientName?.let {
                    viewModel.processIntent(CatalogIntent.CreateOrder(it))
                }
            }
        )
    }

    if (createItemDialogVisibility) {
        AddCatalogItemDialog(
            onDismiss = { createItemDialogVisibility = false },
            intent = viewModel::processIntent
        )
    }
}
