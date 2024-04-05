package com.webchat.webchat.service;

import com.webchat.webchat.dto.ChangePasswordDto;
import com.webchat.webchat.dto.CountMessageDto;
import com.webchat.webchat.dto.ThongKeNguoiDungDto;
import com.webchat.webchat.dto.UserProfileUpdateDto;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.ErrorPojo;
import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.pojo.UserRegisterPojo;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IUserService {
  //  Mono<User> findByUsername(String username);
  //  Mono<User> findById(Integer id);
  //  Flux<User> findAll();
 //   Flux<User> findByGroupUserId(List<Integer> userIds);
    Mono<User> findByEmail(String email);
    Mono<User> saveUser(UserPojo userPojo);
 //   Flux<User> findInRoom(int userId, String roomId);
    Flux<User> findByKeyword(String username, String email);
    Flux<UserPojo> getUsers(String username);
    Mono<UserPojo> getUser(String id);
  //  Flux<CountMessageDto> thongKeTinNhan(String year);
    Mono<ThongKeNguoiDungDto> thongKeNguoiDung();
    Mono<String> updateUser(String image);
    Flux<ErrorPojo> updateProfile(UserProfileUpdateDto userProfileUpdateDto, BindingResult result);
    Flux<ErrorPojo> changePassword(ChangePasswordDto changePasswordDto, BindingResult result);
    Flux<String> getUserOnlineInRoom(String roomId);
    Mono<UserPojo> getUserByUsername(String username);
    Flux<UserPojo> getUserByKeyword(String keyword);
    String signinPage(Model model);
    String signinUnlockPage(Model model);
    String checkSignin(String username, String password, String remember, String unlock, String ip);
    Flux<ErrorPojo> signup(UserRegisterPojo user, BindingResult result);
}
