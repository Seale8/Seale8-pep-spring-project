package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController

public class SocialMediaController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        try {
            Account createdAccount = accountService.registerAccount(account);
            return ResponseEntity.status(200).body(createdAccount);
        } catch (Exception e) {
            if (e.getMessage().equals("Account with this username already exists")) {
                return ResponseEntity.status(409).build();
            } else {
                return ResponseEntity.status(400).build();
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        try {
            Account verifiedAccount = accountService.verifyAccount(account);
            return ResponseEntity.status(200).body(verifiedAccount);
        } catch (Exception e) {
                return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            Message newMessage = messageService.createMessage(message);
            return ResponseEntity.status(200).body(newMessage);
        } catch (Exception e) {
                return ResponseEntity.status(400).build();
        }
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() throws Exception {
            return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable int messageId) throws Exception {
        Optional<Message> message = messageService.getMessagebyId(messageId);
        if (message.isPresent()) {
            return ResponseEntity.ok(message.get()); // Return the message as JSON
        } else {
            return ResponseEntity.ok().build(); // Return an empty body
        }
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) throws Exception {
        try {
            messageService.deleteMessage(messageId);
        return ResponseEntity.status(200).body(1);
        } catch (Exception e) {
            return ResponseEntity.status(200).build();
                }
            }

    @PatchMapping("/messages/{messageId}") 
    public ResponseEntity<Integer> updateMessage(@PathVariable int messageId,@RequestBody Message message) throws Exception {
        try {
        messageService.updateMessage(messageId, message.getMessageText());
        return ResponseEntity.status(200).body(1);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
                }
            }
@GetMapping("/accounts/{accountId}/messages")
public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int accountId) throws Exception {
    List<Message> messages = messageService.getAllMessagesFromAccount(accountId);
    return ResponseEntity.status(200).body(messages); 
}

}


