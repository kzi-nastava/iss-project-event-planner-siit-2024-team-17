package com.ftn.event_hopper.services.messages;

import com.ftn.event_hopper.dtos.messages.ChatMessageDTO;
import com.ftn.event_hopper.dtos.messages.ConversationPreviewDTO;
import com.ftn.event_hopper.dtos.messages.NewChatMessageDTO;
import com.ftn.event_hopper.models.messages.Message;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.repositories.messages.MessageRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MessageRepository messageRepository;


    public boolean sendMessage(NewChatMessageDTO newMessage) {
        Message message = new Message();
        message.setContent(newMessage.getContent().trim());
        message.setTimestamp(LocalDateTime.now());

        Optional<Account> sender = accountRepository.findByEmail(newMessage.getSender());
        Optional<Account> recipient = accountRepository.findByEmail(newMessage.getRecipient());
        if (sender.isEmpty() || recipient.isEmpty()) {
            return false;
        }

        message.setFrom(sender.get());
        message.setTo(recipient.get());

        messageRepository.save(message);
        messageRepository.flush();

        ChatMessageDTO messageForRecipient = new ChatMessageDTO();
        messageForRecipient.setContent(message.getContent());
        messageForRecipient.setTimestamp(message.getTimestamp());
        messageForRecipient.setSender(message.getFrom().getUsername());
        messageForRecipient.setRecipient(message.getTo().getUsername());
        messageForRecipient.setSentByMe(false);

        messagingTemplate.convertAndSendToUser(newMessage.getRecipient(), "/topic/chat", messageForRecipient);

        ChatMessageDTO messageForSender = new ChatMessageDTO();
        messageForSender.setContent(message.getContent());
        messageForSender.setTimestamp(message.getTimestamp());
        messageForSender.setSender(message.getFrom().getUsername());
        messageForSender.setRecipient(message.getTo().getUsername());
        messageForSender.setSentByMe(true);

        messagingTemplate.convertAndSendToUser(newMessage.getSender(), "/topic/chat", messageForSender);

        return true;
    }

    public List<ConversationPreviewDTO> getUsersConversationsPreview() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (account == null) {
            return null;
        }

        List<ConversationPreviewDTO> conversations = new ArrayList<>();
        List<Account> accounts = messageRepository.findDistinctCommunicatedUsers(account.getId());
        for (Account acc : accounts) {
            ConversationPreviewDTO c = new ConversationPreviewDTO();
            c.setUsername(acc.getUsername());
            c.setName(acc.getPerson().getName());
            c.setSurname(acc.getPerson().getSurname());
            c.setProfilePictureUrl(acc.getPerson().getProfilePicture());
            Message latestMessage = messageRepository.findNewestMessageContentBetweenAccounts(account.getId(), acc.getId());
            c.setLastMessage(latestMessage.getContent());
            c.setLastMessageTimestamp(latestMessage.getTimestamp());
            conversations.add(c);
        }

        conversations.sort((c1, c2) -> c2.getLastMessageTimestamp().compareTo(c1.getLastMessageTimestamp()));

        return conversations;
    }

    public List<ChatMessageDTO> getChatHistoryWithUser(String username) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (account == null) {
            return null;
        }

        Optional<Account> otherAccount = accountRepository.findByEmail(username);
        if (otherAccount.isEmpty()) {
            return null;
        }

        List<Message> messages = messageRepository.findMessagesBetweenAccounts(account.getId(), otherAccount.get().getId());
        List<ChatMessageDTO> chatMessages = new ArrayList<>();
        for (Message m : messages) {
            ChatMessageDTO chatMessage = new ChatMessageDTO();
            chatMessage.setContent(m.getContent());
            chatMessage.setTimestamp(m.getTimestamp());
            chatMessage.setSender(m.getFrom().getUsername());
            chatMessage.setRecipient(m.getTo().getUsername());
            chatMessage.setSentByMe(m.getFrom().getId().equals(account.getId()));
            chatMessages.add(chatMessage);
        }

        chatMessages.sort((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()));

        return chatMessages;
    }
}
