package com.erendev.gemini.common

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import org.koin.core.component.KoinComponent

/**
 * Created by erenalpaslan on 30.09.2023
 */
abstract class BaseScreen<VM: BaseViewModel> : KoinComponent {

    protected lateinit var viewModel: VM

    abstract fun getViewModel(): Lazy<VM>

    init {
        viewModel = getViewModel().value
    }

    @Composable
    fun Content() {
        val error by viewModel.error.collectAsState(null)

        if (!error.isNullOrEmpty()) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.removeError()
                },
                title = {},
                text = {
                    Text(text = error ?: "")
                },
                properties = DialogProperties(),
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { viewModel.removeError() }) {
                        Text(text = "Ok")
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )
        }

        val showProgress by viewModel.showProgressIndicator.collectAsState()

        Screen()

        if (showProgress){
            Dialog(onDismissRequest = { /*TODO*/ }) {
                CircularProgressIndicator()
            }
        }
    }

    @Composable
    abstract fun Screen()
}