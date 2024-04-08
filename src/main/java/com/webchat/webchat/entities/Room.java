package com.webchat.webchat.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "image")
    private String image;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "public_key")
    private String publicKey;

    @Column(name = "private_key")
    private String privateKey;

    public Room(String id, String image, String name, String username) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.username = username;
    }

    @OneToMany(mappedBy = "room")
    private List<Message> messages;

    @OneToMany(mappedBy = "room")
    private List<RoomDetail> roomDetails;

    public String getImage() {
        if(image.isEmpty()){
            return null;
        } else {
            return "https://github.com/phonghotkgd2k1/image_public/blob/main/" + image + "?raw=true";
        }
    }
}
