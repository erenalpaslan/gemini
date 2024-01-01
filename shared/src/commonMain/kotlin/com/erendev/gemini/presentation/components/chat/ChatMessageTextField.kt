package com.erendev.gemini.presentation.components.chat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.erendev.gemini.common.resources.Icons
import com.erendev.gemini.common.resources.Strings
import com.erendev.gemini.presentation.components.GeminiBasicTextField
import com.erendev.gemini.presentation.theme.ink_darkest
import com.erendev.gemini.presentation.theme.sky_light
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChatMessageTextField(
    chatText: MutableState<String?>,
    onAnswering: Boolean = false,
    onSend: (String?) -> Unit
) {
    Row(
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp,
            bottom = 24.dp,
            top = 12.dp
        ).imePadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GeminiBasicTextField(
            text = chatText,
            onValueChanged = {
            },
            modifier = Modifier.weight(1f),
            placeholder = Strings.Chat.Message,
            keyboardActions = KeyboardActions(
                onSend = {
                    onSend(chatText.value)
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            ),
            singleLine = true,
            readOnly = onAnswering
        )
        Spacer(Modifier.width(8.dp))
        IconButton(
            onClick = {
                      onSend(chatText.value)
            },
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            enabled = !onAnswering
        ) {
            if (onAnswering) {
                CircularProgressIndicator(modifier = Modifier.padding(8.dp))
            }else {
                Icon(
                    painterResource(Icons.Send),
                    null,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}