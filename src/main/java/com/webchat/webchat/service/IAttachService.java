package com.webchat.webchat.service;

import com.webchat.webchat.entities.Attach;

import java.util.List;

public interface IAttachService {
     String a = new String();
    void saveAttach(List<Attach> attaches);
    List<Attach> findByMessage(String messageId);
    List<Attach> findByRoom(String roomId);
}
