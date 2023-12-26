package com.erendev.gemini.presentation.features.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedOnboardingTheme(
    theme: OnboardingTheme,
    animate: Boolean,
    modifier: Modifier = Modifier,
) {
    val bgColor: Color by animateColorAsState(
        theme.backgroundColor,
        animationSpec = tween(500)
    )
    val themeColor: Color by animateColorAsState(
        theme.themeColor,
        animationSpec = tween(500)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f)
            .background(bgColor)
            .then(modifier),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedVisibility(
            visible = animate,
            enter = expandHorizontally(),
            exit = shrinkHorizontally()
        ) {
            Text(
                theme.title,
                style = MaterialTheme.typography.titleMedium,
                color = themeColor
            )
        }
        Spacer(Modifier.width(4.dp))
        Box(
            modifier = Modifier.size(24.dp)
                .clip(CircleShape)
                .background(themeColor)
        )
    }
}