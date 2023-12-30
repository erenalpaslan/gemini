package com.erendev.gemini.presentation.features.viewdetail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.erendev.gemini.common.BaseScreen
import com.erendev.gemini.presentation.features.home.HomeViewModel
import org.koin.core.component.get
import org.koin.core.component.inject

class ViewDetailScreen: BaseScreen<ViewDetailViewModel>() {
    override fun getViewModel(): Lazy<ViewDetailViewModel> = inject()

    @Composable
    override fun Screen() {
        Text("View detail")
    }

}