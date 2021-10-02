package com.webchat.webchat.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne @JoinColumn(name = "userid")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "content")
    private String content;

    @Column(name = "type")
    private String type;

    @Column(name = "status")
    private String status;

    @Column(name = "time")
    private Date time;

    public String getImage() {
        return "https://github.com/phonghotkgd2k1/image_public/blob/main/" + image + "?raw=true";
    }

    public String getTimeString(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String strDate = dateFormat.format(this.time);
        return strDate;
    }
}
