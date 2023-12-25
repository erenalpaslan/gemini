package com.erendev.gemini.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erendev.gemini.presentation.features.main.components.BottomOptions
import com.erendev.gemini.presentation.theme.ink
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun IconWithLabel(
    option: BottomOptions,
    selected: Boolean = false,
    onOptionClicked: (BottomOptions) -> Unit
) {
    Spacer(Modifier.width(8.dp))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            onClick = {
                onOptionClicked(option)
            },
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = if (selected) MaterialTheme.colorScheme.primary else ink
            )
        ) {
            Box(
                modifier = Modifier.size(44.dp),
                contentAlignment = Alignment.Center
            ) {
                //Icon(painterResource(option.optionIcon), null)
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(
            option.optionName,
            style = MaterialTheme.typography.labelSmall,
            color = if (selected) MaterialTheme.colorScheme.primary else ink
        )
    }
    Spacer(Modifier.width(8.dp))
}