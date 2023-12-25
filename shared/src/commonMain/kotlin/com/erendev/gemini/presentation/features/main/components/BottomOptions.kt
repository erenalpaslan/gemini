package com.erendev.gemini.presentation.features.main.components

/**
 * Created by erenalpaslan on 2.10.2023
 */

open class BottomOptions(
    open val optionName: String,
    open val optionIcon: String
)

sealed class MainBottomOptions (
    val id: Int,
    override val optionName: String,
    override val optionIcon: String
): BottomOptions(optionName, optionIcon) {

    data object Home: MainBottomOptions(
        0,
        "Home",
        ""
    )

    data object NewChat: MainBottomOptions(
        1,
        "New Chat",
        ""
    )

    data object Recent: MainBottomOptions(
        2,
        "Recent",
        ""
    )

    companion object {
        fun getOptionByName(name: String) = when(name) {
            "Home" -> Home
            "New Chat" -> NewChat
            "Recent" -> Recent
            else -> null
        }
    }
}
