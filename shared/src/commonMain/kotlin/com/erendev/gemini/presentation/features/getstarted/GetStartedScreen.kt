package com.erendev.gemini.presentation.features.getstarted

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.bumble.appyx.components.backstack.operation.replace
import com.erendev.gemini.common.BaseScreen
import com.erendev.gemini.common.resources.Strings
import com.erendev.gemini.presentation.navigation.LocalBackStack
import com.erendev.gemini.presentation.navigation.NavTarget
import com.erendev.gemini.presentation.theme.black
import com.erendev.gemini.presentation.theme.blue_darkest
import com.erendev.gemini.presentation.theme.ink_darkest
import com.erendev.gemini.presentation.theme.sky
import com.erendev.gemini.presentation.theme.sky_dark
import com.erendev.gemini.presentation.theme.white
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.get

/**
 * Created by erenalpaslan on 30.09.2023
 */
class GetStartedScreen() : BaseScreen<GetStartedViewModel>() {

    override val viewModel: GetStartedViewModel = get()

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Screen() {
        val backStack = LocalBackStack.current
        val uiState by viewModel.uiState.collectAsState()

        val disclosure = remember {
            buildAnnotatedString {
                withStyle(ParagraphStyle(textAlign = TextAlign.Center)) {
                    pushStyle(SpanStyle(color = white))
                    append("DISCLOSURE")
                    withStyle(style = SpanStyle(color = blue_darkest)) {
                        pushStringAnnotation(tag = "tns", annotation = "tns")
                        append("TERMS_AND_SERVICES")
                    }
                    pushStyle(SpanStyle(color = white))
                    append("&")
                    withStyle(style = SpanStyle(color = blue_darkest)) {
                        pushStringAnnotation(tag = "privacy", annotation = "privacy")
                        append("PRIVACY")
                    }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                containerColor = black.copy(0.3f)
            ) { paddingValues ->
                Column(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .wrapContentHeight()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        ink_darkest.copy(0.4f),
                                        ink_darkest.copy(0.9f),
                                        ink_darkest
                                    )
                                )
                            )
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        when (uiState) {
                            GetStartedUiState.Initial -> {
                                Text(
                                    text = Strings.APP_NAME,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = white
                                )
                                Spacer(Modifier.height(32.dp))
                                Button(
                                    onClick = {
                                        viewModel.onContinueClicked()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Text("Continue")
                                    Spacer(Modifier.width(8.dp))
                                    Icon(Icons.Rounded.ArrowForward, null)
                                }
                                Spacer(Modifier.height(16.dp))
                                ClickableText(
                                    disclosure,
                                    style = MaterialTheme.typography.labelSmall
                                ) { offset ->
                                    disclosure.getStringAnnotations(offset, offset)
                                        .firstOrNull()?.let { span ->
                                            //TODO: Handle browser navigation for tns and pp
                                            backStack.replace(NavTarget.Main)
                                        }
                                }
                            }

                            GetStartedUiState.Login -> {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "LOGIN_TITLE",
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.fillMaxWidth(),
                                        style = MaterialTheme.typography.titleSmall,
                                        color = white
                                    )
                                    Text(
                                        "LOGIN_DESC",
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = sky_dark
                                    )
                                    Spacer(Modifier.height(24.dp))
                                    Button(
                                        onClick = {
                                            //viewModel.onStartClicked()
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = sky
                                        )
                                    ) {
                                        Text(
                                            "Start",
                                            color = ink_darkest,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                    Spacer(Modifier.height(51.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}