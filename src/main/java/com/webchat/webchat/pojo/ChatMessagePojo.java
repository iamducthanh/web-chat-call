package com.webchat.webchat.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessagePojo {
    private String id;
    private MessageType type;
    private String content;
    private String sender;
    private String room;
    private Date timeChat;
    private String statusMessage;
    private String image;
    private String urlFile;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        JOINRETURN,
        ATTACK
    }



    @Override
    public String toString() {
        return "ChatMessagePojo{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", room='" + room + '\'' +
                ", timeChat=" + timeChat +
                '}';
    }
}
