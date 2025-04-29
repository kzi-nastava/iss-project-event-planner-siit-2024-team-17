package com.ftn.event_hopper.controllers.messages;

import com.ftn.event_hopper.dtos.messages.ChatMessageDTO;
import com.ftn.event_hopper.dtos.messages.ConversationPreviewDTO;
import com.ftn.event_hopper.dtos.messages.NewChatMessageDTO;
import com.ftn.event_hopper.services.messages.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MessageController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @Autowired
    public MessageController(SimpMessagingTemplate messagingTemplate, MessageService messageService) {
        this.messagingTemplate = messagingTemplate;
        this.messageService = messageService;
    }

    // Send a private message to a specific user
    @MessageMapping("/chat") // Maps incoming messages to "/socket-subscriber/private-chat"
    public boolean sendPrivateMessage(@Payload NewChatMessageDTO message) {
        return messageService.sendMessage(message);
    }

    @GetMapping(value = "/api/chat/conversations-preview", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsersConversationsPreview() {
        try {
        List<ConversationPreviewDTO> conversations = messageService.getUsersConversationsPreview();
        if (conversations == null) {
            return new ResponseEntity<Collection<ConversationPreviewDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Collection<ConversationPreviewDTO>>(conversations, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/api/chat/history/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getChatHistoryWithUser(@PathVariable String username) {
        try {
            List<ChatMessageDTO> messages = messageService.getChatHistoryWithUser(username);
            if (messages == null) {
                return new ResponseEntity<Collection<ChatMessageDTO>>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Collection<ChatMessageDTO>>(messages, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}