package com.webchat.webchat.service.impl;

import com.webchat.webchat.constant.CodeComfirm;
import com.webchat.webchat.constant.UsersOnline;
import com.webchat.webchat.dto.ChangePasswordDto;
import com.webchat.webchat.dto.ThongKeNguoiDungDto;
import com.webchat.webchat.dto.UserProfileUpdateDto;
import com.webchat.webchat.entities.Friend;
import com.webchat.webchat.entities.User;
import com.webchat.webchat.pojo.ErrorPojo;
import com.webchat.webchat.pojo.UserOnline;
import com.webchat.webchat.pojo.UserPojo;
import com.webchat.webchat.pojo.UserRegisterPojo;
import com.webchat.webchat.repository.UserRepository;
//import com.webchat.webchat.service.IFriendService;
import com.webchat.webchat.service.IFriendService;
import com.webchat.webchat.service.IUserService;
import com.webchat.webchat.utils.CookieUtil;
import com.webchat.webchat.utils.SessionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
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
    //    private final CountMessageService countMessageService;
    private final IFriendService friendService;
    private final HttpServletRequest req;
    private final CookieUtil cookieUtil;
//    private final LocationService locationService;


    @Override
    public Mono<User> saveUser(UserPojo userPojo) {
        return userRepo.findUserById(userPojo.getId()).map(user -> {
            user.setRole(userPojo.getRole());
            user.setStatus(userPojo.isStatus());

            if (!userPojo.isStatus()) {
                template.convertAndSend("/topic/system.adduser", new UserOnline(userPojo.getUsername(), "BLOCK"));
            }

            return user;
        });
    }


    @Override
    public Flux<User> findByKeyword(String username, String email) {
        username = "%" + username + "%";
        email = "%" + email + "%";
        User user = (User) sessionUtil.getObject("USER");
        System.out.println(user.getUsername());

        return userRepo.findUserByUsernameAndEmail(username, email, user.getUsername(), PageRequest.of(0, 30));
    }

    @Override
    public Flux<UserPojo> getUsers(String username) {
        List<UserPojo> userPojos = new ArrayList<>();
        Flux<User> users = userRepo.findAll();
        users.subscribe(user -> {
            if (!user.getUsername().equals(username)) {
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
        return Flux.fromIterable(userPojos);
    }

    @Override
    public Mono<UserPojo> getUser(String id) {
        return userRepo.findUserById(Integer.parseInt(id)).map(user -> {
            UserPojo userPojo = UserPojo.builder()
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
            return userPojo;
        });
    }

//    @Override
//    public List<CountMessageDto> thongKeTinNhan(String year) {
//        List<CountMessageDto> countMessages = new ArrayList<>();
//        for(int i=1;i<13;i++){
//            countMessages.add(new CountMessageDto("thang" + i, countMessageService.count(String.valueOf(i), year)));
//        }
//        return countMessages;
//    }

    @Override
    public Mono<ThongKeNguoiDungDto> thongKeNguoiDung() {
        Flux<User> users = userRepo.findAll();
        ThongKeNguoiDungDto thongKeNguoiDung = new ThongKeNguoiDungDto();
        users.count().subscribe(count -> {
            thongKeNguoiDung.setQuantity(Integer.valueOf(count.toString()));
            thongKeNguoiDung.setOffline(Integer.valueOf(count.toString()) - UsersOnline.usersOnline.size());
        });
        thongKeNguoiDung.setOnline(UsersOnline.usersOnline.size());
        return Mono.just(thongKeNguoiDung);
    }

    @Override
    public Mono<String> updateUser(String image) {
        User user = (User) sessionUtil.getObject("USER");
        user.setImage(image);
        userRepo.save(user);
        return Mono.just("done");
    }

    @Override
    public Flux<ErrorPojo> updateProfile(UserProfileUpdateDto userProfileUpdateDto, BindingResult result) {
        User user = (User) sessionUtil.getObject("USER");
        Flux<ErrorPojo> error = Flux.empty();
        ResourceBundle message = ResourceBundle.getBundle("message");
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError objectError : errors) {
                String messageError = objectError.getDefaultMessage();
                error.concatWith(Mono.just(new ErrorPojo(messageError.substring(messageError.lastIndexOf(".") + 1, messageError.length()), message.getString(messageError))));
            }
        }
        if (!(userProfileUpdateDto.getUsername().trim().isEmpty())) {
            if (!userProfileUpdateDto.getUsername().matches("[A-Za-z0-9]{5,20}")) {
                error.concatWith(Mono.just(new ErrorPojo("username", message.getString("specialCharacters"))));
            } else {
                if (!user.getUsername().equals(userProfileUpdateDto.getUsername())) {
                    Mono<User> userCheck = userRepo.findUserByUsername(userProfileUpdateDto.getUsername());
                    if (userCheck.blockOptional().isEmpty()) {
                        error.concatWith(Mono.just(new ErrorPojo("username", message.getString("UsernameExist"))));
                    }
                }
            }
        }
        if (!(userProfileUpdateDto.getPhone().trim().length() == 0)) {
            if (!userProfileUpdateDto.getPhone().matches("[0]{1}[0-9]{9}")) {
                error.concatWith(Mono.just(new ErrorPojo("phone", message.getString("User.phone"))));
            }
        }
        if (userProfileUpdateDto.getDescription().length() > 100) {
            error.concatWith(Mono.just(new ErrorPojo("description", message.getString("User.description"))));
        }
        if (!String.valueOf(user.getId()).equals(userProfileUpdateDto.getId())) {
            error.concatWith(Mono.just(new ErrorPojo("id", message.getString("User.id"))));
        }
        error.count().subscribe(count -> {
            if (count == 0) {
                saveUpdateUserProfile(user, userProfileUpdateDto);
            }
        });
        return error;
    }

    @Override
    public Flux<ErrorPojo> changePassword(ChangePasswordDto changePasswordDto, BindingResult result) {
        Flux<ErrorPojo> error = Flux.empty();
        User user = (User) sessionUtil.getObject("USER");
        ResourceBundle message = ResourceBundle.getBundle("message");
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError objectError : errors) {
                String messageError = objectError.getDefaultMessage();
                error.concatWith(Mono.just(new ErrorPojo(messageError.substring(messageError.lastIndexOf(".") + 1, messageError.length()), message.getString(messageError))));
            }
        }
        String password = changePasswordDto.getPassword();
        BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
        if (!(changePasswordDto.getPassword().trim().isEmpty())) {
            if (!pass.matches(password, user.getPassword())) {
                error.concatWith(Mono.just(new ErrorPojo("password", message.getString("User.password"))));
            }
        }
        error.count().subscribe(count -> {
            if (count == 0) {
                user.setPassword(pass.encode(changePasswordDto.getNewPassword()));
                userRepo.save(user);
            }
        });
        return error;
    }

    @Override
    public Flux<String> getUserOnlineInRoom(String roomId) {
        User user = (User) sessionUtil.getObject("USER");
        Flux<User> users = userRepo.findUserByUserIdAndRoomDetails(user.getId(), roomId);
        Flux<String> userOnline = Flux.empty();
        users.subscribe(u -> {
            if (u.isOnline()) {
                userOnline.concatWith(Mono.just(u.getUsername()));
            }
        });
        return userOnline;
    }

    @Override
    public Mono<UserPojo> getUserByUsername(String username) {
        return userRepo.findUserByUsername(username)
                .map(u -> UserPojo.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .fullName(u.getFullname())
                        .email(u.getEmail())
                        .image(u.getImage())
                        .lastonline(u.getLastonline())
                        .gender(u.isGender())
                        .role(u.getRole())
                        .birthDate(u.getBirthDate())
                        .friendStatus("")
                        .online(u.isOnline())
                        .phone(u.getPhone())
                        .descreption(u.getDescription())
                        .birthdayString(u.getBirthDayString())
                        .build()
                );
    }

    @Override
    public Flux<UserPojo> getUserByKeyword(String keyword) {
        Flux<UserPojo> userPojos = Flux.empty();
        User myUser = (User) sessionUtil.getObject("USER");
        if (!(keyword.trim().length() == 0)) {
            keyword = "%" + keyword + "%";
            User userLogin = (User) sessionUtil.getObject("USER");
            Flux<User> users = userRepo.findUserByUsernameAndEmail(keyword, keyword, userLogin.getUsername(), PageRequest.of(0, 30));
            if (users != null) {
                users.subscribe(user -> {
                    Mono<Friend> friend = friendService.findFriendBy2User(myUser.getUsername(), user.getUsername());
                    friend.subscribe(f -> {
                        String statusFriend = "ON";
                        if (f != null && f.getStatus().equals("WAIT") && f.getUser().getUsername().equals(myUser.getUsername())) {
                            statusFriend = "WAIT"; // bật hủy lời mời kết bạn
                        } else if (f != null && f.getStatus().equals("WAIT") && f.getUser().getUsername().equals(user.getUsername())) {
                            statusFriend = "AGREE"; // bật nút đồng ý kết bạn
                        } else if (f != null) {
                            statusFriend = "OFF"; // tắt
                        }
                        userPojos.concatWith(Mono.just(new UserPojo(
                                user.getId(),
                                user.getUsername(),
                                user.getFullname(),
                                user.getEmail(),
                                user.getImage(),
                                user.getLastonline(),
                                user.isGender(),
                                user.getRole(),
                                user.getBirthDate(),
                                statusFriend,
                                UsersOnline.usersOnline.get(user.getUsername()) != null ? true : false,
                                user.getPhone(),
                                user.getDescription(),
                                user.getBirthDayString()
                        )));
                    });

                });
            }
        }
        return userPojos;
    }

    @Override
    public String signinPage(Model model) {
        String status = req.getParameter("status");
        if (status != null && status.equals("login_false")) {
            req.setAttribute("errormessage", "Thông tin tài khoản hoặc mật khẩu không chính xác!");
        }
        if (status != null && status.equals("logout")) {
            cookieUtil.add("username", "username", 0);
            cookieUtil.add("remember", "on", 0);
            cookieUtil.add("password", "password", 0);
        } else {
            String username = cookieUtil.get("username");
            String password = cookieUtil.get("password");
            String remember = cookieUtil.get("remember");

            Mono<User> user = userRepo.findUserByUsername(username);
            BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
            if (user != null) {
                if (pass.matches(password, user.block().getPassword())) {
                    if (remember != null && remember.equals("on")) {
                        model.addAttribute("username", username);
                        model.addAttribute("password", password);
                        model.addAttribute("rememberOn", remember);
                    }
                }
            }
        }
        return "views/acount/signin";
    }

    @Override
    public String signinUnlockPage(Model model) {
        cookieUtil.add("username", "", 0);
        cookieUtil.add("remember", "on", 0);
        cookieUtil.add("password", "", 0);
        model.addAttribute("unlock", "on");
        req.setAttribute("errormessage", "Chúng tôi nhận thấy phiên đăng nhập bất thường trên thiết bị của bạn, vui lòng đăng nhập lại!");
        return "views/acount/signin";
    }

    @Override
    public String checkSignin(String username, String password, String remember, String unlock, String ip) {
        if (remember.equals("true")) {
            int hour = 60 * 60 * 24 * 10;
            cookieUtil.add("username", username, hour);
            cookieUtil.add("remember", "on", 0);
            cookieUtil.add("password", password, hour);
        } else {
            cookieUtil.add("username", username, 0);
            cookieUtil.add("remember", "on", 0);
            cookieUtil.add("password", password, 0);
        }
        String message = "";
        Mono<User> userMono = userRepo.findUserByUsername(username);
        User user = userMono.block();
        if (user == null) {
            message = "Thông tin tài khoản hoặc mật khẩu không chính xác!";
        } else {
            BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
            if (!pass.matches(password, user.getPassword())) {
                message = "Thông tin tài khoản hoặc mật khẩu không chính xác!";
            } else if (!user.isStatus()) {
                message = "Tài khoản của bạn đã bị khóa!!";
            } else {
                if (unlock.equals("on")) {
//                    Location location = locationService.findByUserAndIp(user.getUsername(), ip);
//                    if (location != null) {
//                        location.setStatus("ON");
//                        locationService.saveLocation(location);
//                    }
                }
            }
        }
        return message;
    }

    @Override
    public Flux<ErrorPojo> signup(UserRegisterPojo user, BindingResult result) {
        Flux<ErrorPojo> error = Flux.empty();
        ResourceBundle message = ResourceBundle.getBundle("message");
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            for (ObjectError objectError : errors) {
                String messageError = objectError.getDefaultMessage();
                error.concatWith(Mono.just(new ErrorPojo(messageError.substring(messageError.lastIndexOf(".") + 1, messageError.length()), message.getString(messageError))));
            }
        }
        if (!(user.getPassword().trim().length() == 0) && !(user.getComfirmPassword().trim().length() == 0)) {
            if (!user.getPassword().equals(user.getComfirmPassword())) {
                error.concatWith(Mono.just(new ErrorPojo("comfirmPassword", message.getString("Unlike.user.comfirmPassword"))));
            }
        }
        if (!(user.getUsername().trim().length() == 0)) {
            if (!user.getUsername().matches("[A-Za-z0-9]{5,20}")) {
                error.concatWith(Mono.just(new ErrorPojo("username", message.getString("specialCharacters"))));
            } else {
                Mono<User> userCheck = userRepo.findUserByUsername(user.getUsername());
                userCheck.hasElement()
                        .subscribe(hasElement -> {
                            if (hasElement) {
                                error.concatWith(Mono.just(new ErrorPojo("username", message.getString("UsernameExist"))));
                            }
                        });
            }
        }
        String email = user.getEmail();
        if (email.indexOf("@") > 0 && email.indexOf(".") > 0) {
            Mono<User> userCheck = userRepo.findUserByEmail(user.getEmail());
            userCheck.hasElement().subscribe(hasElement -> {
                if (hasElement) {
                    error.concatWith(Mono.just(new ErrorPojo("email", message.getString("EmailExist"))));
                }
            });
        }
        if (!(user.getCode().trim().length() == 0)) {
            String code = String.valueOf(CodeComfirm.codeComfirm.get(user.getEmail()));
            if (!code.equals(user.getCode())) {
                error.concatWith(Mono.just(new ErrorPojo("code", message.getString("User.code"))));
            }
        }
        error.hasElements().subscribe(hasElement -> {
            if(!hasElement){
                User userSignup = new User();
                userSignup.setUsername(user.getUsername());
                userSignup.setFirstName(user.getFirstName());
                userSignup.setLastName(user.getLastName());
                userSignup.setEmail(user.getEmail());
                BCryptPasswordEncoder pass = new BCryptPasswordEncoder();
                userSignup.setPassword(pass.encode(user.getPassword()));
                userSignup.setImage("avt.png");
                userSignup.setGender(user.isGender());
                userSignup.setRole("ROLE_USER");
                userSignup.setDescription("");
                userSignup.setPhone("");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                userSignup.setLastonline(new Date());
                try {
                    Date birthDate = sdf.parse(user.getBirthDate());
                    userSignup.setBirthDate(birthDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userRepo.save(userSignup);
            }
        });
        return error;
    }

    public void saveUpdateUserProfile(User user, UserProfileUpdateDto userProfileUpdateDto) {
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

    @Override
    public Mono<User> findByEmail(String email) {
        return userRepo.findUserByEmail(email);
    }
}
