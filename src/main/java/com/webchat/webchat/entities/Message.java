package com.webchat.webchat.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "message", uniqueConstraints = {@UniqueConstraint(columnNames = {"userid", "roomid"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne @JoinColumn(name = "userid")
    private User user;

    @ManyToOne @JoinColumn(name = "roomid")
    private Room room;

    @Column(name = "type")
    private String type;

    @Column(name = "time")
    private Date time;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private String status;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "message")
    private List<Attach> attachList;

    public Message(User user, Room room, String type, Date time, String content, String status) {
        this.user = user;
        this.room = room;
        this.type = type;
        this.time = time;
        this.content = content;
        this.status = status;
    }

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", user=" + user.getUsername() +
                ", room=" + room.getId() +
                ", type='" + type + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getTimeChat(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat fm = new SimpleDateFormat("HH:mm");
        String strDate = dateFormat.format(this.time);

        String timeChat = "";
        Date now= new Date();
        String strDateNow = dateFormat.format(now);
        if(strDate.substring(0, 10).equals(strDateNow.substring(0, 10))){
            timeChat = fm.format(this.time);
        } else {
            timeChat = dateFormat.format(this.time);
        }


        long diff = this.time.getTime() - now.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return timeChat;
    }

}
