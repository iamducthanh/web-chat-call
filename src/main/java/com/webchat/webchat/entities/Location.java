package com.webchat.webchat.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "location", uniqueConstraints = {@UniqueConstraint(columnNames = {"userid"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne @JoinColumn(name = "userid")
    private User user;

    @Column(name = "ip")
    private String ip;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "address")
    private String address;

    @Column(name = "time")
    private Date time;

    @Column(name = "status")
    private String status;

    public Location(User user, String ip, String longitude, String latitude, String address, Date time) {
        this.user = user;
        this.ip = ip;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.time = time;
    }

    public String getLastTime(){
        String last = "";
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now= new Date();

        long diff = now.getTime() - this.time.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
//        System.out.print(diffDays + " days, ");
//        System.out.print(diffHours + " hours, ");
//        System.out.print(diffMinutes + " minutes, ");
//        System.out.print(diffSeconds + " seconds.");
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
}
