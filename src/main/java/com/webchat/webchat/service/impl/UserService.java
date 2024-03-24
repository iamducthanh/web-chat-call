package com.webchat.webchat.service.impl;

import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.dto.ChangePasswordDto;
import com.webchat.webchat.dto.CountMessageDto;
import com.webchat.webchat.dto.ThongKeNguoiDungDto;
import com.webchat.webchat.dto.UserProfileUpdateDto;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.ErrorPojo;
import com.webchat.webchat.pojo.UserOnline;
import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.repository.UserRepository;
import com.webchat.webchat.service.IUserService;
import com.webchat.webchat.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepo;
    private final SessionUtil sessionUtil;
    private final SimpMessagingTemplate template;
    private final CountMessageService countMessageService;


    @Override
    public User findByUsername(String username) {
        List<User> list = userRepo.findUserByUsername(username);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public User findById(Integer id) {
        List<User> list = userRepo.findUserById(id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public List<User> findByGroupUserId(List<Integer> userIds) {
        List<User> list = userRepo.findUserByGroupUserId(userIds);
        return list.isEmpty() ? null : list;
    }

    @Override
    public User findByEmail(String email) {
        List<User> list = userRepo.findUserByEmail(email);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void saveUser(UserPojo userPojo) {
        List<User> list = userRepo.findUserById(userPojo.getId());
        User user =  list.isEmpty() ? null : list.get(0);
        user.setRole(userPojo.getRole());
        user.setStatus(userPojo.isStatus());

        if(!userPojo.isStatus()){
            template.convertAndSend("/topic/system.adduser", new UserOnline(userPojo.getUsername(), "BLOCK"));
        }
    }

    @Override
    public List<User> findInRoom(int userId, String roomId) {
        List<User> list = userRepo.findUserInRoom(userId, roomId);
        return list.isEmpty() ? null : list;
    }

    @Override
    public List<User> findByKeyword(String username, String email) {
        username = "%" + username + "%";
        email = "%" + email + "%";
        User user = (User) sessionUtil.getObject("USER");
        System.out.println(user.getUsername());
        List<User> list = userRepo.findUserByKeyword(username, email, user.getUsername(), PageRequest.of(0, 30));
        return list.isEmpty() ? null : list;
    }

    @Override
    public List<UserPojo> getUsers(String username) {
        List<User> users = userRepo.findAll();
        List<UserPojo> userPojos = new ArrayList<>();
        users.forEach((user) -> {
            System.out.println(user.toString());
            if(!user.getUsername().equals(username)){
                userPojos.add(new UserPojo(
                        user.getId(),
                        user.getUsername(),
                        user.getFullname(),
                        user.getEmail(),
                        user.getImage(),
                        user.getLastonline(),
                        user.isGender(),
                        user.getRole(),
                        user.getBirthDate(),
                        user.getLastOnlineString(),
                        user.isOnline(),
                        user.getPhone(),
                        user.getDescription(),
                        user.getBirthDayString(),
                        user.isStatus()
                ));
            }
        });
        return userPojos;
    }

    @Override
    public UserPojo getUser(String id) {
        List<User> list = userRepo.findUserById(Integer.parseInt(id));
        User user =  list.isEmpty() ? null : list.get(0);
        return UserPojo.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullname())
                .email(user.getEmail())
                .image(user.getImage())
                .lastonline(user.getLastonline())
                .gender(user.isGender())
                .role(user.getRole())
                .birthDate(user.getBirthDate())
                .friendStatus(user.getLastOnlineString())
                .online(user.isOnline())
                .phone(user.getPhone())
                .descreption(user.getDescription())
                .birthdayString(user.getBirthDayString())
                .status(user.isStatus())
                .build();
    }

    @Override
    public List<CountMessageDto> thongKeTinNhan(String year) {
        List<CountMessageDto> countMessages = new ArrayList<>();
        for(int i=1;i<13;i++){
            countMessages.add(new CountMessageDto("thang" + i, countMessageService.count(String.valueOf(i), year)));
        }
        return countMessages;
    }

    @Override
    public ThongKeNguoiDungDto thongKeNguoiDung() {
        List<User> users = userRepo.findAll();
        ThongKeNguoiDungDto thongKeNguoiDung = new ThongKeNguoiDungDto();
        thongKeNguoiDung.setQuantity(users.size());
        thongKeNguoiDung.setOnline(UsersOnline.usersOnline.size());
        thongKeNguoiDung.setOffline(users.size() - UsersOnline.usersOnline.size());
        return thongKeNguoiDung;
    }

    @Override
    public String updateUser(String image) {
        User user = (User) sessionUtil.getObject("USER");
        user.setImage(image);
        userRepo.save(user);
        return "done";
    }

    @Override
    public List<ErrorPojo> updateProfile(UserProfileUpdateDto userProfileUpdateDto, BindingResult result) {
        User user = (User) sessionUtil.getObject("USER");
        List<ErrorPojo> error = new ArrayList<>();
        ResourceBundle message = ResourceBundle.getBundle("message");
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError objectError : errors) {
                String messageError = objectError.getDefaultMessage();
                error.add(new ErrorPojo(messageError.substring(messageError.lastIndexOf(".") + 1, messageError.length()), message.getString(messageError)));
            }
        }
        if(!(userProfileUpdateDto.getUsername().trim().length() == 0)){
            if(!userProfileUpdateDto.getUsername().matches("[A-Za-z0-9]{5,20}")){
                error.add(new ErrorPojo("username",message.getString("specialCharacters")));
            } else {
                if(!user.getUsername().equals(userProfileUpdateDto.getUsername())){
                    List<User> list = userRepo.findUserByUsername(userProfileUpdateDto.getUsername());
                    User userCheck =  list.isEmpty() ? null : list.get(0);
                    if(userCheck != null){
                        error.add(new ErrorPojo("username",message.getString("UsernameExist")));
                    }
                }
            }
        }
        if(!(userProfileUpdateDto.getPhone().trim().length() == 0)){
            if(!userProfileUpdateDto.getPhone().matches("[0]{1}[0-9]{9}")){
                error.add(new ErrorPojo("phone",message.getString("User.phone")));
            }
        }
        if(userProfileUpdateDto.getDescription().length() > 100){
            error.add(new ErrorPojo("description",message.getString("User.description")));
        }
        if(!String.valueOf(user.getId()).equals(userProfileUpdateDto.getId())){
            error.add(new ErrorPojo("id",message.getString("User.id")));
        }
        if(error.size() == 0){
            saveUpdateUserProfile(user, userProfileUpdateDto);
        }
        return error;
    }

    @Override
    public List<ErrorPojo> changePassword(ChangePasswordDto changePasswordDto, BindingResult result) {
        List<ErrorPojo> error = new ArrayList<>();
        User user = (User) sessionUtil.getObject("USER");
        ResourceBundle message = ResourceBundle.getBundle("message");
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError objectError : errors) {
                String messageError = objectError.getDefaultMessage();
                error.add(new ErrorPojo(messageError.substring(messageError.lastIndexOf(".") + 1, messageError.length()), message.getString(messageError)));
            }
        }
        String password = changePasswordDto.getPassword();
        BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
        if(!(changePasswordDto.getPassword().trim().length() == 0)){
            if(!pass.matches(password, user.getPassword())){
                error.add(new ErrorPojo("password",message.getString("User.password")));
            }
        }
        if(error.isEmpty()){
            user.setPassword(pass.encode(changePasswordDto.getNewPassword()));
            userRepo.save(user);
        }
        return error;
    }

    @Override
    public List<String> getUserOnlineInRoom(String roomId) {
        User user = (User) sessionUtil.getObject("USER");
        List<User> users = userRepo.findUserInRoom(user.getId(), roomId);
        List<String> userOnline = new ArrayList<>();
        for (User user1 : users) {
            if (user1.isOnline()) {
                userOnline.add(user1.getUsername());
            }
        }
        return userOnline;
    }

    @Override
    public UserPojo getUserByUsername(String username) {
        List<User> list = userRepo.findUserByUsername(username);
        User user =  list.isEmpty() ? null : list.get(0);
        UserPojo userPojo = new UserPojo(
                user.getId(),
                user.getUsername(),
                user.getFullname(),
                user.getEmail(),
                user.getImage(),
                user.getLastonline(),
                user.isGender(),
                user.getRole(),
                user.getBirthDate(),
                "",
                user.isOnline(),
                user.getPhone(),
                user.getDescription(),
                user.getBirthDayString()
        );
        return userPojo;
    }

    public void saveUpdateUserProfile(User user, UserProfileUpdateDto userProfileUpdateDto){
        user.setPhone(userProfileUpdateDto.getPhone());
        user.setUsername(userProfileUpdateDto.getUsername());
        user.setDescription(userProfileUpdateDto.getDescription());
        user.setFirstName(userProfileUpdateDto.getFirstName());
        user.setLastName(userProfileUpdateDto.getLastName());
        user.setGender(userProfileUpdateDto.getGender());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date birthDate = sdf.parse(userProfileUpdateDto.getBirthday());
            user.setBirthDate(birthDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userRepo.save(user);
    }
}
