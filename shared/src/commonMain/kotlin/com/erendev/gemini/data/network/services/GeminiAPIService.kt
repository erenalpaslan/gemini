package com.erendev.gemini.data.network.services

import com.erendev.gemini.BuildKonfig
import com.erendev.gemini.common.types.GeminiAITypes
import com.erendev.gemini.data.DataResult
import com.erendev.gemini.data.entity.GeminiContent
import com.erendev.gemini.data.entity.GeminiMessage
import com.erendev.gemini.data.entity.PromptFeedback
import com.erendev.gemini.data.entity.generateContent.GeminiGenerateContentRequest
import com.erendev.gemini.data.entity.generateContent.GeminiGenerateContentResponse
import com.erendev.gemini.data.network.client
import com.erendev.gemini.data.toDataResult
import comerendevgemini.Message
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class GeminiAPIService {

    suspend fun generateContent(
        list: List<GeminiContent>
    ): DataResult<GeminiGenerateContentResponse> {
        return client.post {
            url("v1beta/models/${GeminiAITypes.GEMINI_PRO.type}:generateContent")
            parameter("key", BuildKonfig.API_KEY)
            setBody(
                GeminiGenerateContentRequest(
                    contents = list
                )
            )
        }.toDataResult<GeminiGenerateContentResponse>()
    }

}