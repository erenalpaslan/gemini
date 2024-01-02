package com.erendev.gemini.presentation.features.viewdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumble.appyx.components.backstack.operation.pop
import com.erendev.gemini.common.BaseScreen
import com.erendev.gemini.common.resources.Drawables
import com.erendev.gemini.common.resources.Strings
import com.erendev.gemini.presentation.components.GeminiTopBar
import com.erendev.gemini.presentation.navigation.LocalBackStack
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.component.inject

class ViewDetailScreen : BaseScreen<ViewDetailViewModel>() {
    override fun getViewModel(): Lazy<ViewDetailViewModel> = inject()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
    @Composable
    override fun Screen() {
        val backStack = LocalBackStack.current
        Scaffold(
            topBar = {
                GeminiTopBar(
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                backStack.pop()
                            }
                        ) {
                            Icon(Icons.Rounded.Close, null)
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                        .padding(24.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(Drawables.AppLogo),
                        null,
                        modifier = Modifier.fillMaxWidth(0.3f).padding(bottom = 10.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(Strings.ChatTitle, style = MaterialTheme.typography.titleMedium)
                }
                Spacer(Modifier.height(12.dp))
                Text(Strings.ViewDetail.ModelInfo, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(24.dp))
                Text(Strings.ViewDetail.DefaultAiModel, style = MaterialTheme.typography.labelLarge)
                Text(Strings.ViewDetail.ModelDescription, style = MaterialTheme.typography.labelMedium)
            }
        }
    }

}