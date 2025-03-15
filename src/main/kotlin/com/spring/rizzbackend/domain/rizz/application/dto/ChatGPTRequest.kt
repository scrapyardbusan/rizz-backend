package com.spring.rizzbackend.domain.rizz.application.dto

data class ChatGPTRequest(val model: String, private val prompt: String) {
    val messages: MutableList<Message> = ArrayList<Message>()

    init {
        messages.add(Message("user", prompt))
    }

}
