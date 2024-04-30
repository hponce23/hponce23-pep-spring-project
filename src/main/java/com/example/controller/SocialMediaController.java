package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import javafx.util.Pair;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    /**
     * Register a new account.
     * 
     * @param account The account containing the account information.
     * @return ResponseEntity with a success or failure status.
     */
    @PostMapping(value = "/register")
    ResponseEntity<Account> register(@RequestBody Account account) {
        if (!accountService.accountExists(account.getUsername())) {
            if (!account.getUsername().isEmpty() && account.getPassword().length() > 3) {
                Account createdAccount = accountService.registerAccount(account);
                return ResponseEntity.ok(createdAccount);
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * Log in user if the give account information is valid.
     * 
     * @param account The account containing the account information.
     * @return ResponseEntity with a success or failure status.
     */
    @PostMapping("/login")
    ResponseEntity<Account> login(@RequestBody Account account) {
        Account loginAccount = accountService.login(account);
        if (loginAccount != null) {
            return ResponseEntity.ok(loginAccount);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    /**
     * Create a new Message.
     * 
     * @param message The message to create.
     * @return ResponseEntity with a success or failure status.
     */
    @PostMapping("/messages")
    ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if (accountService.accountExists(message.getPostedBy())) {
            Message createdMessage = messageService.createMessage(message);
            if (createdMessage != null) {
                return ResponseEntity.ok(createdMessage);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    /**
     * Retrieve all messages.
     * 
     * @return ResponseEntity with a list of all messages.
     */
    @GetMapping("/messages")
    ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    /**
     * Retrieve message with the given message ID.
     * 
     * @param message_id The ID of the message to retrieve.
     * @return ResponseEntity with a success or failure status.
     */
    @GetMapping("/messages/{message_id}")
    ResponseEntity<Optional<Message>> getMessageGivenMessageId(@PathVariable("message_id") Integer message_id) {
        Optional<Message> message = messageService.getMessageGivenMessageId(message_id);
        if (message.isEmpty()) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(message);
    }

    /**
     * Retrieves messages from appropriate user Account given the given Account ID.
     * 
     * @param posted_by is the account ID.
     * @return ResponseEntity with the list of messages posted by the account.
     */
    @GetMapping("/accounts/{account_id}/messages")
    ResponseEntity<List<Message>> getMessagesGivenAccountId(@PathVariable("account_id") Integer posted_by) {
        return ResponseEntity.ok(messageService.getAllMessagesGivenAccountId(posted_by));
    }

    /**
     * Delete the message with the given ID.
     * 
     * @param message_id The ID of the message we want to delete.
     * @return ResponseEntity with a success or failure status.
     */
    @DeleteMapping("/messages/{message_id}")
    ResponseEntity<Integer> deleteMessageGivenMessageId(@PathVariable("message_id") Integer message_id) {
        Optional<Message> message = messageService.deleteMessageGivenMessageId(message_id);
        if (message.isEmpty()) {
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(1);
    }

    /**
     * Update the message with the given ID.
     * 
     * @param message    The message the contains the given message text.
     * @param message_id The ID of the message we want to update.
     * @return ResponseEntity with a success or failure status.
     */
    @PatchMapping("/messages/{message_id}")
    ResponseEntity<Integer> updateMessageGivenMessageId(@RequestBody Message message,
            @PathVariable("message_id") Integer message_id) {
        Message possibleMessage = messageService.updateMessageGivenMessageId(message, message_id);
        if (possibleMessage != null) {
            return ResponseEntity.ok(1);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

}
