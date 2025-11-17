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

    public record PresentationRequest(String topic, int count){}

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest chatRequest) {
        String response = chatClient.prompt().user(chatRequest.message()).call().content();
        return new ChatResponse(response);
    }

    @PostMapping("/funny")
    public ChatResponse funnyAI(@RequestBody ChatRequest chatRequest) {
        String response = chatClient.prompt().
                system("Your are funny and sarcastic").user(chatRequest.message()).call().content();
        return new ChatResponse(response);
    }

    @PostMapping("/presentation")
    public ChatResponse presentation(@RequestBody PresentationRequest presentationRequest) {
        String prompt =
                "I want to give a presentation about " + presentationRequest.topic() + ". " +
                        "Give me " + presentationRequest.count() + " title suggestions.";
        String response = chatClient.prompt().user(prompt).call().content();
        return new ChatResponse(response);
    }
}
