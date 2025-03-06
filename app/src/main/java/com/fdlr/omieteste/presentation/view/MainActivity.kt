package com.fdlr.omieteste.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.fdlr.omieteste.presentation.navigation.AppNavigation
import com.fdlr.omieteste.presentation.theme.OmieTesteTheme
import com.fdlr.omieteste.presentation.utils.loadThemePreference
import com.fdlr.omieteste.presentation.utils.saveThemePreference

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val isDarkTheme = loadThemePreference(this)
        setContent {
            var darkThemeState by remember { mutableStateOf(isDarkTheme) }

            OmieTesteTheme(darkTheme = darkThemeState) {
                AppNavigation(
                    isDarkMode = darkThemeState,
                    onDarkModeChange = {
                        darkThemeState = it
                        saveThemePreference(this, it)
                    }
                )
            }
        }
    }
}
