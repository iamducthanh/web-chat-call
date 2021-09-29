package com.webchat.webchat.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notification")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne @JoinColumn(name = "userid")
    private User user;

    @ManyToOne @JoinColumn(name = "friendid")
    private User friend;

    @Column(name = "status")
    private String status;
}
