package com.webchat.webchat.service;

import com.webchat.webchat.entities.Location;

import java.util.List;

public interface ILocationService {
    public void saveLocation(Location location);
    public List<Location> findByUser(String username);
    public Location findByUserAndIp(String username, String ip);
}
