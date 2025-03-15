package com.spring.rizzbackend.domain.rizz.application

import com.spring.rizzbackend.domain.rizz.application.dto.ChatGPTRequest
import com.spring.rizzbackend.domain.rizz.application.dto.ChatGPTResponse
import com.spring.rizzbackend.global.response.BaseResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class RizzService {

    @Autowired
    @Qualifier("openaiConfig")
    private lateinit var template: RestTemplate

    @Value("\${openai.model}")
    private lateinit var model: String

    @Value("\${openai.api.url}")
    private lateinit var apiURL: String

    fun chat(
        text: String
    ): BaseResponse<ChatGPTResponse> {
        val request = ChatGPTRequest(model = model, prompt = "질문에 대한 대답을 해줘 : $text")
        val chatGPTResponse = template.postForObject(
            apiURL, request,
            ChatGPTResponse::class.java
        )
        val keyWord: String = chatGPTResponse?.choices?.get(0)?.message?.content
            ?: throw IllegalStateException("No response from AI")
        return BaseResponse(
            message = "성공",
            data = chatGPTResponse
        )
    }

}
