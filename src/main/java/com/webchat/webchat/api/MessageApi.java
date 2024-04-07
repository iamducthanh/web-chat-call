package com.webchat.webchat.api;

import com.webchat.webchat.dto.FileAttackDto;
import com.webchat.webchat.dto.MessagePageDto;
import com.webchat.webchat.pojo.MessagePojo;
import com.webchat.webchat.service.impl.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageApi {
    private final MessageService messageService;

    @GetMapping("/api/message")
    public List<MessagePojo> getMessage(MessagePageDto messagePageDto) {
        return messageService.getMessage(messagePageDto);
    }

    @PostMapping("/message_direct/save")
    public List<FileAttackDto> saveMessage(
            @RequestParam("content") String content,
            @RequestParam("room") String roomId,
            @RequestParam("attack") String attack) throws IOException {
        return messageService.saveMessage(content, roomId, attack);
    }

    @GetMapping("/api/files")
    public List<String> getFiles(@RequestParam("room") String roomId) {
        return messageService.getFiles(roomId);
    }
}
