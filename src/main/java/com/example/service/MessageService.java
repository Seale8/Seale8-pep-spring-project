package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) throws Exception{
        if(message.getMessageText().isBlank()){
            throw new Exception("Message text is blank");
        }
        if(message.getMessageText().length()>255){
            throw new Exception("Message text is too long");
        }
        if(!accountRepository.existsById(message.getPostedBy())){
            throw new Exception("Account doesnt exist");
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() throws Exception{
      
        return messageRepository.findAll();
    }

    public Optional<Message> getMessagebyId(int id) throws Exception{
      
        return messageRepository.findById(id);
    }

    public void deleteMessage(int id) throws Exception{
      messageRepository.deleteById(id);
    }

    public void updateMessage(int id, String text) throws Exception{
        if(text.isBlank()){
            throw new Exception("Message text is blank");
        }
        if(text.length()>255){
            throw new Exception("Message text is too long");
        }
        if(!messageRepository.existsById(id)){
            throw new Exception("Message doesnt exist");
        }
        Optional<Message> optionalMessage = messageRepository.findById(id);
        Message message = optionalMessage.get();
        message.setMessageText(text);
        messageRepository.save(message);
        
      }

      public List<Message> getAllMessagesFromAccount(int id) throws Exception{
      
        return messageRepository.findMessageByPostedBy(id);
    }

}
