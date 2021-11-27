package com.webchat.webchat;

import com.webchat.webchat.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author GMO_ThanhND
 * @version 1.0
 * @since 11/24/2021
 */
@SpringBootTest
public class TestTest {
    @Autowired
    private IUserService userService;
    @Test
    public void test1(){
//        assertEquals(12, userService.findAll().size());
        assertEquals(12, userService.findAll().size());
    }
}
