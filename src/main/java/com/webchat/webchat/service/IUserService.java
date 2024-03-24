package com.webchat.webchat.service;

import com.webchat.webchat.dto.ChangePasswordDto;
import com.webchat.webchat.dto.CountMessageDto;
import com.webchat.webchat.dto.ThongKeNguoiDungDto;
import com.webchat.webchat.dto.UserProfileUpdateDto;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.ErrorPojo;
import com.webchat.webchat.pojo.UserPojo;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface IUserService {
    User findByUsername(String username);
    User findById(Integer id);
    List<User> findAll();
    List<User> findByGroupUserId(List<Integer> userIds);
    User findByEmail(String email);
    void saveUser(UserPojo userPojo);
    List<User> findInRoom(int userId, String roomId);
    List<User> findByKeyword(String username, String email);
    List<UserPojo> getUsers(String username);
    UserPojo getUser(String id);
    List<CountMessageDto> thongKeTinNhan(String year);
    ThongKeNguoiDungDto thongKeNguoiDung();
    String updateUser(String image);
    List<ErrorPojo> updateProfile(UserProfileUpdateDto userProfileUpdateDto, BindingResult result);
    List<ErrorPojo> changePassword(ChangePasswordDto changePasswordDto, BindingResult result);
    List<String> getUserOnlineInRoom(String roomId);
}
