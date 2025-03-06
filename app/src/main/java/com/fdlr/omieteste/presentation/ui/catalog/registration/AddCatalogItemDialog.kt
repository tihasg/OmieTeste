package com.fdlr.omieteste.presentation.ui.catalog.registration

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.fdlr.omieteste.R
import com.fdlr.omieteste.domain.model.CatalogItem
import com.fdlr.omieteste.presentation.intent.CatalogIntent
import com.fdlr.omieteste.presentation.ui.catalog.components.CurrencyInputField
import com.fdlr.omieteste.presentation.utils.saveImageToInternalStorage
import com.fdlr.omieteste.presentation.utils.toCurrency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCatalogItemDialog(
    intent: (CatalogIntent) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    val context = LocalContext.current

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    val saveAction = {
        var imagePath: String? = null
        selectedImageUri?.let {
            imagePath = saveImageToInternalStorage(context, it)
        }

        if (name.isNotEmpty() && price.isNotEmpty()) {
            val item = CatalogItem(
                name = name,
                description = description,
                price = price.toDouble(),
                imagePath = imagePath
            )
            intent(CatalogIntent.CreateNewCatalogItem(item))
            onDismiss()
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.add_item_title)) },
                    actions = {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Fechar"
                            )
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {

                Box {
                    Image(
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                        imageVector = Icons.Outlined.Image,
                        contentDescription = "Adicionar Imagem",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 16.dp)
                    )
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 16.dp)
                            .clickable {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        contentScale = ContentScale.Crop
                    )
                }

                OutlinedTextField(
                    value = name,
                    maxLines = 1,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.item_name_hint)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Sentences
                    )
                )
                OutlinedTextField(
                    value = description,
                    maxLines = 1,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.item_description_hint)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                        capitalization = KeyboardCapitalization.Sentences
                    )
                )

                CurrencyInputField(
                    value = price.toDoubleOrNull() ?: 0.0,
                    onValueChange = { price = it.toString() },
                    label = stringResource(R.string.item_price_hint),
                    modifier = Modifier.fillMaxWidth(),
                    navigateToNext = saveAction
                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    enabled = name.isNotEmpty() && price.toCurrency() > 0.0,
                    onClick = saveAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(stringResource(R.string.btt_save))
                }
            }
        }
    }
}
