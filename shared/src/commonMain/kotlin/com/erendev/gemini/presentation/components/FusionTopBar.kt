package com.erendev.gemini.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.erendev.gemini.presentation.theme.geminiTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeminiTopBar(
    title: String? = null,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    divider: @Composable () -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
) {
    Column {
        TopAppBar(
            title = {
                if (!title.isNullOrEmpty()) {
                    Text(text = title, style = geminiTypography.bodyLarge)
                }
            },
            actions = {
                actions()
            },
            navigationIcon = {
                navigationIcon()
            },
            colors = colors
        )
        divider()
    }
}