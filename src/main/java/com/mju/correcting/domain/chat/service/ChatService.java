package com.mju.correcting.domain.chat.service;

import com.mju.correcting.domain.chat.Category;
import com.mju.correcting.domain.chat.domain.ChatLog;
import com.mju.correcting.domain.chat.dto.GetChatsRes;
import com.mju.correcting.domain.chat.repository.ChatLogRepository;
import com.mju.correcting.domain.chat.domain.ChatRoom;
import com.mju.correcting.domain.chat.repository.ChatRoomRepository;
import com.mju.correcting.domain.user.domain.User;
import com.mju.correcting.domain.user.repository.UserRepository;
import com.mju.correcting.domain.weakness.domain.Weakness;
import com.mju.correcting.domain.weakness.repository.WeaknessRepository;
import com.mju.correcting.global.common.error.BaseCode;
import com.mju.correcting.global.common.exception.CustomException;
import com.mju.correcting.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChatService {
    @Value("${gpt.secret}")
    private String API_KEY;
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final ChatLogRepository chatLogRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final WeaknessRepository weaknessRepository;
    private final UserRepository userRepository;

    @Transactional
    public String getCompletion(Long chatRoomId, String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        // 프롬프트 설정 + 이전 대화 상황
        List<String> previousMessages = new ArrayList<>();

        previousMessages.add("우리는 특정상황에 맞춰서 영어 대화를 하고 있는 중이야. 틀린 경우, 답변 앞에 []안에 [품사, 어순, 형식, 시제, 화법, 접속법, 부정문, 불규칙 활용, 구식문법] 이 9가지중 어디인지 적어주면 돼. 그리고 수정해줘. 예를 들어, [어순] I would like to order a hamburger. 맞았을 경우, [정답] 이라고 적어주고 답변 해줘!");

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new CustomException(BaseCode.NOT_FOUND_CHATROOM));
        previousMessages.add("우리의 대화주제는" + chatRoom.getInterest().name());

        List<ChatLog> byChatRoomId = chatLogRepository.findFirst3ByChatRoomId(chatRoomId);
        byChatRoomId.forEach(chatLog -> previousMessages.add(chatLog.getQuestion()));

        // 영어 필터링
        if (!isEnglish(prompt)) {
            return "대화 상황에 맞는 입력을 해주세요.";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + API_KEY);

        StringBuffer messagesJson = new StringBuffer("[");
        for (int i = 0; i < previousMessages.size(); i++) {
            messagesJson.append("{\"role\": \"user\", \"content\": \"")
                    .append(previousMessages.get(i))
                    .append("\"}");
            if (i < previousMessages.size() - 1) {
                messagesJson.append(",");
            }
        }
        messagesJson.append(",{\"role\": \"user\", \"content\": \"").append(prompt).append("\"}]");

        String requestBody = "{ \"model\": \"gpt-3.5-turbo\", \"messages\": " + messagesJson.toString() + "}";

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            JSONArray choices = jsonResponse.getJSONArray("choices");
            JSONObject firstChoice = choices.getJSONObject(0);
            JSONObject message = firstChoice.getJSONObject("message");
            String content = message.getString("content");

            // 결과값 처리
            Pattern pattern = Pattern.compile("\\[(.*?)\\]");
            Matcher matcher = pattern.matcher(content);

            while (matcher.find()) {
                String found = matcher.group(1);
                try {
                    Category category = Category.valueOf(found);

                    User user = userRepository.findByUsername(SecurityUtil.getCurrentUserName())
                            .orElseThrow(() -> new CustomException(BaseCode.UNSIGN_USERNAME_OR_PHONE));

                    weaknessRepository.save(Weakness.builder()
                            .category(category)
                            .user(user)
                            .wrongSentence(prompt)
                            .correctSentence(content)
                            .build());

                } catch (IllegalArgumentException e) {
                }
            }

            // chat_log에 저장
            chatLogRepository.save(ChatLog.builder()
                    .chatRoom(chatRoom)
                    .question(prompt)
                    .answer(content)
                    .build());

            return content;
        } else {
            throw new RuntimeException("Failed to call OpenAI API: " + response.getStatusCode());
        }
    }

    public List<GetChatsRes> getChats(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new CustomException(BaseCode.NOT_FOUND_CHATROOM));

        List<ChatLog> chatLogs = chatLogRepository.findByChatRoom(chatRoom);

        return chatLogs.stream()
                .map(GetChatsRes::new)
                .collect(Collectors.toList());
    }


    private boolean isEnglish(String text) {
        Pattern englishPattern = Pattern.compile("[A-Za-z0-9.,?!'\" ]+");
        return englishPattern.matcher(text).matches();
    }
}
