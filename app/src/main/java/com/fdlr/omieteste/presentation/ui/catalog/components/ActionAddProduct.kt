package com.fdlr.omieteste.presentation.ui.catalog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fdlr.omieteste.R
import com.fdlr.omieteste.domain.model.CartItem
import com.fdlr.omieteste.presentation.intent.CatalogIntent

@Composable
fun ActionAddProduct(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    isInCart: Boolean = true,
    intent: (CatalogIntent) -> Unit
) {
    var initialQuantity = cartItem.quantity

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surfaceBright)
    ) {
        if (!isInCart) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable {
                        intent(CatalogIntent.AddItemToCart(cartItem.product))
                        initialQuantity = 1
                    }
                    .padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AddShoppingCart,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 10.dp)
                )
                Text(
                    text = stringResource(R.string.btt_add),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        } else {
            if (initialQuantity == 1) {
                Icon(
                    tint = MaterialTheme.colorScheme.error,
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            intent(CatalogIntent.RemoveItemFromCart(cartItem))
                            initialQuantity--
                        }
                        .padding(10.dp)
                )
            } else {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Filled.Remove,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            if (initialQuantity <= 1) {
                                intent(CatalogIntent.RemoveItemFromCart(cartItem))
                            }
                            initialQuantity--
                            intent(
                                CatalogIntent.UpdateCartQuantity(
                                    initialQuantity,
                                    cartItem
                                )
                            )
                        }
                        .padding(10.dp)
                )
            }
            Text(
                text = initialQuantity.toString(),
                fontWeight = FontWeight.ExtraBold,
            )
            Icon(
                tint = MaterialTheme.colorScheme.primary,
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        initialQuantity++
                        intent(CatalogIntent.UpdateCartQuantity(initialQuantity, cartItem))
                    }
                    .padding(10.dp)
            )
        }
    }
}
