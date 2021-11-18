package com.webchat.webchat.api;

import com.webchat.webchat.dto.CountMessageDto;
import com.webchat.webchat.service.impl.CountMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GMO_ThanhND
 * @version 1.0
 * @since 11/16/2021
 */
@RestController
@RequestMapping("/api/admin")
public class DashBoardApi {
    @Autowired
    private CountMessageService countMessageService;

    @GetMapping("/thong-ke-tin-nhan")
    public ResponseEntity<List<CountMessageDto>> thongKeTinNhan(@RequestParam String year){
        List<CountMessageDto> countMessages = new ArrayList<CountMessageDto>();
        for(int i=1;i<13;i++){
            countMessages.add(new CountMessageDto("thang" + i, countMessageService.count(String.valueOf(i), year)));
        }
        return ResponseEntity.ok().body(countMessages);
    }
}
