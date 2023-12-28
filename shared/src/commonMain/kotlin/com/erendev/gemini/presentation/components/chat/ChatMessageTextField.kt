package com.erendev.gemini.presentation.components.chat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
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
    onSend: (String?) -> Unit
) {
    val chatText = remember {
        mutableStateOf<String?>(null)
    }

    Row(
        modifier = Modifier.padding(
            start = 8.dp,
            end = 8.dp,
            bottom = 24.dp,
            top = 12.dp
        ),
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
            )
        )
        Spacer(Modifier.width(8.dp))
        IconButton(
            onClick = {
                      onSend(chatText.value)
            },
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = sky_light
            )
        ) {
            Icon(
                painterResource(Icons.Edit),
                null,
                tint = ink_darkest,
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}