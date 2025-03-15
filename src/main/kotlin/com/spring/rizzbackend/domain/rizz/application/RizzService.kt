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
        val prompt = """
            너가 내 여자친구라고 생각하고 그녀의 언어를 해석해줘.
            "$text" 이 질문에 대해서 고민해보고 안에 궁금하닥 했던 부분이 해석 전 부분은 "$text"의 따옴표 사이 것만 파싱해줘.
            **무슨 뜻인지** 번역만 한 뒤에, **간단한 부가 설명**을 해주고, 화난 정도를 **레벨 1~10 사이**로 알려줘.
            그리고 제일 중요한거!!! 그녀의 화를 풀 수 있을만한 엉뚱하고 웃어 넘길 수 있을만한 대처법을 BEST.1 ~ BEST.3 으로 알려줘 (+ 간단한 팁도 함께)

            답변 양식은 아래를 따라줘. 꼭 아래에 맞게 대답해야 해?

            <예시>

            레벨 : 6

            언어 해석:
            ”좀 지친다. 나 그냥 잘게"  → “지금 너랑 이야기하는 게 힘들다. 더 대화하면 화만 날 것 같으니까 그냥 잔다.”

            간단한 부가 설명:
            추가로 "잘게"라고 한 걸 보면, 감정 소모로 인해 지쳐서 일단 피하는 느낌.

            해결법:
            BEST 1. "여보세요? 고객님? 제 마음속에 계신데요? 여보세요? 잠드셨나요?”
            BEST 1’s tip : 이걸 톡으로 보내거나 음성 메시지로 장난스럽게 보내면, 살짝 피식할 수도 있음

            BEST 2. "아니 벌써 꿈나라 가신다고요? 내 꿈에 초대장 보냈는데, 확인하고 오시면 됩니다.”
            BEST 2’s tip : 장난스럽게 꿈에서 만나자고 하면 풀릴 수도 있음

            BEST 3. "(귀여운 강아지나 고양이 움짤 보내면서) 이 친구가 내 상태야. 벌써 반성 중이야.”
            BEST 3’s tip : 귀여운 이미지로 감정 완화 유도
        """.trimIndent()

        val request = ChatGPTRequest(model = model, prompt = prompt)
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
