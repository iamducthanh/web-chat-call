package com.webchat.webchat.api;

import com.webchat.webchat.dto.CountMessageDto;
import com.webchat.webchat.dto.ThongKeNguoiDungDto;
import com.webchat.webchat.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author GMO_ThanhND
 * @version 1.0
 * @since 11/16/2021
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class DashBoardApi {

    private final IUserService userService;

    @GetMapping("/thong-ke-tin-nhan")
    public ResponseEntity<List<CountMessageDto>> thongKeTinNhan(@RequestParam String year){
        return ResponseEntity.ok().body(userService.thongKeTinNhan(year));
    }

    @GetMapping("/thong-ke-nguoi-dung")
    public ResponseEntity<ThongKeNguoiDungDto> thongKeNguoiDung(){
        return ResponseEntity.ok().body(userService.thongKeNguoiDung());
    }
}
