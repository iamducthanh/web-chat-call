package com.webchat.webchat.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webchat.webchat.constant.AttackFile;
import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.dto.FileAttackDto;
import com.webchat.webchat.dto.MessagePageDto;
import com.webchat.webchat.entities.*;
import com.webchat.webchat.pojo.MessagePojo;
import com.webchat.webchat.pojo.UserConnectPojo;
import com.webchat.webchat.service.impl.AttachService;
import com.webchat.webchat.service.impl.MessageService;
import com.webchat.webchat.service.impl.RoomDetailService;
import com.webchat.webchat.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequiredArgsConstructor
public class MessageApi {
    private final MessageService messageService;

    @GetMapping("/api/message")
    public List<MessagePojo> getMessage(MessagePageDto messagePageDto) throws Exception {
        return messageService.getMessage(messagePageDto);
    }

    @PostMapping("/message_direct/save")
    public List<FileAttackDto> saveMessage(
            @RequestParam("content") String content,
            @RequestParam("room") String roomId,
            @RequestParam("attack") String attack) throws Exception {
        return messageService.saveMessage(content, roomId, attack);
    }

    @GetMapping("/api/files")
    public List<String> getFiles(@RequestParam("room") String roomId) {
        return messageService.getFiles(roomId);
    }
}
