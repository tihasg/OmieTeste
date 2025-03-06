package com.fdlr.omieteste.presentation.ui.catalog.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.fdlr.omieteste.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWithTextField(
    showBottomSheet: Boolean,
    onDismissRequest: (String?) -> Unit
) {
    var clientName by remember { mutableStateOf(TextFieldValue("")) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(showBottomSheet) {
        if (showBottomSheet) {
            clientName = TextFieldValue("")
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.surfaceBright,
            onDismissRequest = {
                onDismissRequest(null)
            },
            sheetState = sheetState
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.End) {
                Text(stringResource(R.string.customer_hint), modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        if (clientName.text.isNotEmpty()) {
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onDismissRequest(clientName.text)
                                }
                            }
                        }
                    })
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    enabled = clientName.text.isNotEmpty(),
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                onDismissRequest(clientName.text)
                            }
                        }
                    }
                ) {
                    Text(stringResource(R.string.btt_finish))
                }
            }
        }

        LaunchedEffect(Unit) {
            if (showBottomSheet) {
                focusRequester.requestFocus()
                keyboardController?.show()
            }
        }
    }
}

