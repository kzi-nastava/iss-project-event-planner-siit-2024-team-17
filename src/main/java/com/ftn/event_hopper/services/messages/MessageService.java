package com.ftn.event_hopper.services.messages;

import com.ftn.event_hopper.dtos.messages.ChatMessageDTO;
import com.ftn.event_hopper.dtos.messages.ConversationPreviewDTO;
import com.ftn.event_hopper.dtos.messages.NewChatMessageDTO;
import com.ftn.event_hopper.models.messages.Message;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.repositories.messages.MessageRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private MessageRepository messageRepository;


    public boolean sendMessage(NewChatMessageDTO newMessage) {
        Message message = new Message();
        message.setContent(newMessage.getContent().trim());
        message.setTimestamp(LocalDateTime.now());

        Optional<Account> sender = accountRepository.findByUsername(newMessage.getSender());
        Optional<Account> recipient = accountRepository.findByUsername(newMessage.getRecipient());
        if (sender.isEmpty() || recipient.isEmpty()) {
            return false;
        }

        message.setFrom(sender.get());
        message.setTo(recipient.get());

        messageRepository.save(message);
        messageRepository.flush();

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

        return chatMessages;
    }
}
