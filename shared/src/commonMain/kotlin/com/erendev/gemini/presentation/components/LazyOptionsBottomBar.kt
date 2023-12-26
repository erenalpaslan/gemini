package com.erendev.gemini.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erendev.gemini.presentation.features.main.components.BottomOptions
import com.erendev.gemini.presentation.features.main.components.MainBottomOptions
import org.jetbrains.compose.resources.ExperimentalResourceApi

/**
 * Created by erenalpaslan on 1.10.2023
 */
@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LazyOptionsBottomBar(
    options: List<BottomOptions> = emptyList(),
    onOptionClicked: (BottomOptions) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)
    ) {
        LazyRow(
            modifier = Modifier.padding(8.dp)
        ) {
            items(options) { option ->
                IconWithLabel(
                    option = option
                ) { clickedOption ->
                    onOptionClicked(clickedOption)
                }
            }
        }
    }
}

@Composable
fun MainOptionsBottomBar(
    selectedIndex: Int,
    onOptionClicked: (BottomOptions) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconWithLabel(
                selected = selectedIndex == MainBottomOptions.Home.id,
                option = MainBottomOptions.Home
            ) { clickedOption ->
                onOptionClicked(clickedOption)
            }

            IconWithLabel(
                selected = selectedIndex == MainBottomOptions.NewChat.id,
                option = MainBottomOptions.NewChat
            ) { clickedOption ->
                onOptionClicked(clickedOption)
            }

            IconWithLabel(
                selected = selectedIndex == MainBottomOptions.Recent.id,
                option = MainBottomOptions.Recent
            ) { clickedOption ->
                onOptionClicked(clickedOption)
            }
        }
    }
}