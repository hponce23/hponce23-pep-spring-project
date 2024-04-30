package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * This method persists a Message to the message table in the database if the
     * message text is valid.
     * 
     * @param message The Message to be persisted.
     * @return The persisted Message.
     */
    public Message createMessage(Message message) {
        if (message.getMessageText().length() < 255 && !message.getMessageText().isEmpty()) {
            return messageRepository.save(message);
        }
        return null;
    }

    /**
     * This method retrieves all messages from the database.
     * 
     * @return A list of all messages.
     */
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * This method retrieves the message with the matching message ID from the
     * database.
     * 
     * @param message_id The ID of the message to retrieve.
     * @return An Optional containing the message if found.
     */
    public Optional<Message> getMessageGivenMessageId(int message_id) {
        return messageRepository.findById(message_id);
    }

    /**
     * This method retrieves all messages posted by a user with the given account
     * ID.
     * 
     * @param account_id The ID of the account.
     * @return A list of messages posted by the specified account.
     */
    public List<Message> getAllMessagesGivenAccountId(int account_id) {
        return messageRepository.findMessagesByPostedId(account_id);
    }

    /**
     * This method deletes message with the given message ID if found in the
     * database.
     * 
     * @param message_id The ID of the message to delete.
     * @return An Optional containing the deleted message if found.
     */
    public Optional<Message> deleteMessageGivenMessageId(int message_id) {
        Optional<Message> message = messageRepository.findById(message_id);
        if (message.isPresent()) {
            messageRepository.deleteById(message_id);
            return message;
        }
        return Optional.empty();
    }

    /**
     * This method checks if a message with the given message ID exists in the
     * database.
     * If the message exists, updates its message text with the new message text if
     * valid.
     * 
     * @param message    The updated message.
     * @param message_id The ID of the message to update.
     * @return The updated message if successful.
     */
    public Message updateMessageGivenMessageId(Message message, int message_id) {
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if (optionalMessage.isPresent() && !message.getMessageText().isEmpty()
                && message.getMessageText().length() < 255) {
            Message possibleMessage = optionalMessage.get();
            possibleMessage.setMessageText(message.getMessageText());
            return messageRepository.save(possibleMessage);
        }
        return null;
    }

}
