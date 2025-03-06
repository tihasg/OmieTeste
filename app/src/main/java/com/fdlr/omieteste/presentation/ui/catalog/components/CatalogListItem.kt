package com.fdlr.omieteste.presentation.ui.catalog.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.fdlr.omieteste.domain.model.CartItem
import com.fdlr.omieteste.presentation.intent.CatalogIntent
import com.fdlr.omieteste.presentation.utils.toBrazilianCurrency

@Composable
fun CatalogListItem(
    item: CartItem,
    intent: (CatalogIntent) -> Unit,
    index: Int,
    catalogItems: List<CartItem>
) {
    Column {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            item.product.imagePath?.let {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(6.dp))
                )

            } ?: run {
                Image(
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                    imageVector = Icons.Outlined.Image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(6.dp))
                )
            }
            Column(
                modifier = Modifier.padding(start = 8.dp).weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    item.product.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                )
                if (item.product.description.isNotEmpty())
                    Text(
                        item.product.description,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    item.product.price.toBrazilianCurrency(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            ActionAddProduct(
                modifier = Modifier.padding(4.dp),
                cartItem = CartItem(
                    id = item.id,
                    product = item.product,
                    quantity = item.quantity
                ),
                isInCart = item.quantity > 0,
                intent = intent
            )
        }
        if (index < catalogItems.size - 1) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}