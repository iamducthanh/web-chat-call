//package com.webchat.webchat.controller.acount;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.webchat.webchat.service.ILockAcountService;
//import com.webchat.webchat.utils.UploadUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequiredArgsConstructor
//public class LockAcountController {
//    private final ILockAcountService lockAcountService;
//    @GetMapping("/lock_acount")
//    public String lockAcountPage() {
//        return "views/acount/lockacount";
//    }
//
//
//    @PostMapping("/testImage")
//    public void readImage(String dataI) throws IOException {
//        lockAcountService.readImage(dataI);
//    }
//}
