package com.spring.rizzbackend.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.web.client.RestTemplate

@Configuration
class OpenAIConfig {

    @Value("\${openai.api.key}")
    private lateinit var apiKey: String

    @Bean(name = ["openaiConfig"])
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.interceptors.add(ClientHttpRequestInterceptor { request, body, execution ->
            request.headers.add("Authorization", "Bearer $apiKey")
            execution.execute(request, body)
        })
        return restTemplate
    }
}
