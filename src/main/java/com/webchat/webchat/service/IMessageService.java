package com.webchat.webchat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.webchat.webchat.dto.FileAttackDto;
import com.webchat.webchat.dto.MessagePageDto;
import com.webchat.webchat.entities.Message;
import com.webchat.webchat.entities.Room;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.MessagePojo;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IMessageService {
    List<Message> findByRoom(String roomId, Pageable pageable);
    List<Message> findAllByRoom(String roomId);
    void deleteMessage(List<Message> messages);
    void setStatusMessage(String roomId, String username, String status);
    List<Message> findByRoomAndUserAndStatus(String roomId, String username, String status);
    Message findMessageLast(String roomId);
    int countMessageSend(String roomId, String myUsername, String username);
    List<MessagePojo> getMessage(MessagePageDto messagePageDto);
    List<FileAttackDto> saveMessage(String content, String roomId, String attack) throws JsonProcessingException;
    List<String> getFiles(String roomId);
}
