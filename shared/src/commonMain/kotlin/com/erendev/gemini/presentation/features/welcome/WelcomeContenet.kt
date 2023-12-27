package com.erendev.gemini.presentation.features.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.erendev.gemini.common.resources.Icons
import com.erendev.gemini.common.resources.Strings
import com.erendev.gemini.presentation.components.list.WelcomeItem

@Composable
fun WelcomeContent(
    onContinueClicked: () -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(it)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .weight(1f).verticalScroll(scrollState)
            ) {
                Text(
                    Strings.Welcome.WELCOME_TITLE,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(24.dp))
                Text(Strings.Welcome.WELCOME_DESC, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(16.dp))
                WelcomeItem(
                    Strings.Welcome.TITLE_ONE,
                    Strings.Welcome.DESC_ONE,
                    Icons.MessageSearch
                )
                Spacer(Modifier.height(16.dp))
                WelcomeItem(
                    Strings.Welcome.TITLE_SECOND,
                    Strings.Welcome.DESC_SECOND,
                    Icons.Setting
                )
                Spacer(Modifier.height(16.dp))
                WelcomeItem(
                    Strings.Welcome.TITLE_THIRD,
                    Strings.Welcome.DESC_THIRD,
                    Icons.Lock
                )
            }
            Button(
                onClick = {
                    onContinueClicked()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(Strings.Button.Continue)
            }
        }
    }
}