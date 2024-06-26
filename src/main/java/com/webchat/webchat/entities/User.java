package com.webchat.webchat.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.webchat.webchat.constant.UsersOnline;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "image")
    private String image;

    @Column(name = "lastonline")
    private Date lastonline;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "description")
    private String description;

    @Column(name = "phone")
    private String phone;

    @Column(name = "role")
    private String role;

    @Column(name = "birthdate")
    private Date birthDate;

    @Column(name = "status")
    private boolean  status;

    @OneToMany(mappedBy = "user")
    private List<Message> messages;

    @OneToMany(mappedBy = "user")
    private List<Location> locations;

    @OneToMany(mappedBy = "user")
    private List<Friend> users;

    @OneToMany(mappedBy = "friend")
    private List<Friend> friends;

    @OneToMany(mappedBy = "user")
    private List<Notification> user;

    @OneToMany(mappedBy = "user")
    private List<RoomDetail> roomDetails;

    public boolean isOnline(){
        if(UsersOnline.usersOnline.get(this.username) != null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", role='" + role + '\'' +
                '}';
    }

    public String getImage() {
        return "https://github.com/phonghotkgd2k1/image_public/blob/main/" + image + "?raw=true";
    }

    public String getImageFirst() {
        return image;
    }

    public String getFullname(){
        return this.lastName + " " + this.firstName;
    }

    public String getBirthDayString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.birthDate);
    }

    public String getLastOnlineString(){
        String last = "";
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now= new Date();

        long diff = now.getTime() - this.lastonline.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if(diffDays != 0){
            last = "Hoạt động "+ diffDays + " ngày trước";
        } else if(diffHours != 0){
            last = "Hoạt động "+ diffHours + " giờ trước";
        } else if(diffMinutes != 0){
            last = "Hoạt động "+ diffMinutes + " phút trước";
        } else if(diffSeconds != 0){
            last = "Hoạt động "+ diffSeconds + " giây trước";
        }
        return last;
    }

    public User(Integer id, String role, Boolean status) {
        this.id = id;
        this.role = role;
        this.status = status;
    }
}
