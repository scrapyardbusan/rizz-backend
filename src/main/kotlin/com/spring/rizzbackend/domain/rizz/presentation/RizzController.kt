package com.spring.rizzbackend.domain.rizz.presentation

import com.spring.rizzbackend.domain.rizz.application.RizzService
import com.spring.rizzbackend.domain.rizz.application.dto.ChatGPTResponse
import com.spring.rizzbackend.global.response.BaseResponse
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/rizz")
class RizzController (
    private val rizzService: RizzService
) {

    @PostMapping
    fun chat(@RequestBody text:String): BaseResponse<ChatGPTResponse> {
        return rizzService.chat(text)
    }
}
