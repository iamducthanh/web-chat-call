//package com.webchat.webchat.service.impl;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.webchat.webchat.service.ILockAcountService;
//import com.webchat.webchat.utils.UploadUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class LockAcountService implements ILockAcountService {
//    private final UploadUtil uploadUtil;
//
//    @Override
//    public void readImage(String dataI) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<String> listData = Arrays.asList(objectMapper.readValue(dataI, String[].class));
//        for(String data:listData){
//            UUID id = UUID.randomUUID();
//            String name = String.valueOf(id);
//            uploadUtil.saveFile(data.toString(), name);
//        }
//    }
//}
