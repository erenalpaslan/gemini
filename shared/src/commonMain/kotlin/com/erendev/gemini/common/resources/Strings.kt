package com.erendev.gemini.common.resources

/**
 * Created by erenalpaslan on 25.05.2023
 */
object Strings {

    const val APP_NAME = "Gemini"

    object Button {
        const val Continue = "Continue"
        const val Delete = "Delete"
        const val Rename = "Rename"
        const val ViewDetails = "View Details"
    }

    object Onboarding {
        fun letsDo(action: String): String = "Let's $action"
        const val CREATE = "create"
        const val GO = "go"
        const val DESIGN = "design"
        const val BRAINSTORM = "brainstorm"
        const val EXPLORE = "explore"
        const val CHIT_CHAT = "chit-chat"
    }

    object Welcome {
        const val WELCOME_TITLE = "Welcome to\nChatGemini"
        const val WELCOME_DESC = "This app uses Gemini AI, syncs your history in your device and brings latest improvements from Gemini"
        const val TITLE_ONE = "The Gemini era: enabling a future of innovation"
        const val DESC_ONE = "This is a significant milestone in the development of AI, and the start of a new era for us at Google as we continue to rapidly innovate and responsibly advance the capabilities of our models."
        const val TITLE_SECOND = "Next-generation capabilities"
        const val DESC_SECOND = "Gemini is natively multimodal, pre-trained from the start on different modalities"
        const val TITLE_THIRD = "Built with responsibility and safety at the core"
        const val DESC_THIRD = "Building upon Google’s AI Principles and the robust safety policies across google's products, new protections to account for Gemini’s multimodal capabilities"
    }

    object Chat {
        const val Message = "Message"
        const val Search = "Search"
    }
}