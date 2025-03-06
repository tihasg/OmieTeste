package com.fdlr.omieteste.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fdlr.omieteste.R
import com.fdlr.omieteste.domain.model.OrderWithItems
import com.fdlr.omieteste.presentation.state.OrderHistoryState
import com.fdlr.omieteste.presentation.ui.common.AboutAppDialog
import com.fdlr.omieteste.presentation.ui.common.LoadingScreen
import com.fdlr.omieteste.presentation.utils.toBrazilianCurrency
import com.fdlr.omieteste.presentation.utils.toBrazilianDateTime
import com.fdlr.omieteste.presentation.viewmodels.OrderHistoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    viewModel: OrderHistoryViewModel,
    onNavigateToCatalog: () -> Unit,
    onDarkModeChange: (Boolean) -> Unit,
    isDarkMode: Boolean
) {
    val state by viewModel.ordersState.collectAsState()
    var totalPrice by remember { mutableStateOf(0.0) }
    var aboutDialogOpen by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.history_title)) },
                actions = {
                    IconButton(onClick = {
                        onDarkModeChange(!isDarkMode)
                    }) {
                        Icon(
                            if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                            contentDescription = "Adicionar ao Catálogo"
                        )
                    }

                    IconButton(onClick = {
                        aboutDialogOpen = true
                    }) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Sobre o App"
                        )
                    }
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCatalog,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar ao Catálogo")
            }
        },
        bottomBar = {
            if (totalPrice > 0.0)
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surfaceBright,
                    modifier = Modifier.fillMaxWidth(),
                    content = {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.total_orders),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                            Text(
                                text = totalPrice.toBrazilianCurrency(),
                                textAlign = TextAlign.End,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                )
        }
    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {

            when (state) {
                is OrderHistoryState.Loading -> LoadingScreen()

                is OrderHistoryState.OrdersHistory -> {
                    val orders = (state as OrderHistoryState.OrdersHistory).items
                    totalPrice = (state as OrderHistoryState.OrdersHistory).totalPrice

                    Column(modifier = Modifier.fillMaxSize()) {
                        if (orders.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    stringResource(R.string.no_order),
                                    textAlign = TextAlign.Center
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(bottom = 100.dp),
                            ) {
                                items(orders.reversed()) { order ->
                                    OrderCardItem(order)
                                }
                            }
                        }
                    }
                }

                is OrderHistoryState.Error -> {
                    Text("Erro: ${(state as OrderHistoryState.Error).message}")
                }
            }
        }
    }

    AboutAppDialog(
        visible = aboutDialogOpen,
        onDismiss = { aboutDialogOpen = false }
    )
}


@Composable
fun OrderCardItem(order: OrderWithItems) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .clickable { isExpanded = !isExpanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.order_label))
                    Text(
                        "#${order.id}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
                Text(order.date.toBrazilianDateTime(), style = MaterialTheme.typography.labelSmall)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.customer_label, order.customerName),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelMedium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    stringResource(R.string.details),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelSmall
                )
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = if (isExpanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = "Expandir"
                )
            }

            if (isExpanded) {
                Text(
                    stringResource(R.string.items_label),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                order.items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 2.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        item.imagePath?.let {
                            AsyncImage(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                model = it,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        } ?: run {
                            Image(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                imageVector = Icons.Outlined.Image,
                                contentDescription = null
                            )
                        }
                        Text(
                            item.name, modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text("${item.quantity}x - ${item.price.toBrazilianCurrency()}")
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.total_label))
                Text(
                    order.totalPrice.toBrazilianCurrency(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                )
            }

        }
    }
}
