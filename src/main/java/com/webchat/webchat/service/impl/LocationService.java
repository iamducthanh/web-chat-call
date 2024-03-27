//package com.webchat.webchat.service.impl;
//
//import com.webchat.webchat.entities.Friend;
//import com.webchat.webchat.entities.Location;
//import com.webchat.webchat.repository.LocationRepository;
//import com.webchat.webchat.service.ILocationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class LocationService implements ILocationService {
//    @Autowired
//    private LocationRepository repo;
//
//    @Override
//    public void saveLocation(Location location) {
//        repo.save(location);
//    }
//
//    @Override
//    public List<Location> findByUser(String username) {
//        List<Location> list = repo.findByUser(username);
//        return list.isEmpty() ? null : list;
//    }
//
//    @Override
//    public Location findByUserAndIp(String username, String ip) {
//        List<Location> list = repo.findByUserAndIp(username, ip);
//        return list.isEmpty() ? null : list.get(0);
//    }
//}
