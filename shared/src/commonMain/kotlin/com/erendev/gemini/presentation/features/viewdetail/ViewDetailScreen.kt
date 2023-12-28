package com.erendev.gemini.presentation.features.viewdetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.erendev.gemini.common.BaseScreen
import org.koin.core.component.get

class ViewDetailScreen: BaseScreen<ViewDetailViewModel>() {
    override val viewModel: ViewDetailViewModel
        get() = get()

    @Composable
    override fun Screen() {
        Text("View detail")
    }

}