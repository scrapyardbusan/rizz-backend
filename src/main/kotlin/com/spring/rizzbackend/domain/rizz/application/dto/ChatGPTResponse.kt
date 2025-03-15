package com.spring.rizzbackend.domain.rizz.application.dto

data class ChatGPTResponse (val choices: List<Choice>? = null){

    data class Choice( val index:Int = 0, val message: Message? = null)

}
