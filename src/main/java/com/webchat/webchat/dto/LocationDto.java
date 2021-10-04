package com.webchat.webchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private String ip;
    private String city;
    private String country;
    private String latitude;
    private String longitude;

}
