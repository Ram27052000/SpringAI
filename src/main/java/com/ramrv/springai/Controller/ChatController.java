package com.ramrv.springai.Controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatclient) {
        this.chatClient = chatclient.build();
    }

    public record ChatRequest(String message) {
    }

    public record ChatResponse(String message) {
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest chatRequest) {
        String response = chatClient.prompt().user(chatRequest.message()).call().content();
        return new ChatResponse(response);
    }
}
