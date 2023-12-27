package com.erendev.gemini.presentation.components.menu

import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.erendev.gemini.common.resources.Icons
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChatMoreMenu(
    onViewDetailClicked: () -> Unit,
    onRenameClicked: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    IconButton(onClick = {
        expanded = true
    }) {
        Icon(painterResource(Icons.More), null)
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            onClick = {},
            text = {
                Text("View Details")
            },
            leadingIcon = {
                Icon(painterResource(Icons.InfoCircle), null)
            }
        )
        Divider()
        DropdownMenuItem(
            onClick = {},
            text = {
                Text("Rename")
            },
            leadingIcon = {
                Icon(painterResource(Icons.Edit), null)
            }
        )
        DropdownMenuItem(
            onClick = {},
            text = {
                Text("Delete")
            },
            leadingIcon = {
                Icon(painterResource(Icons.Trash), null)
            }
        )
    }
}