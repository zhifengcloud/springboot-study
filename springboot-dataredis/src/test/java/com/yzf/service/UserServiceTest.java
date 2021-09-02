package com.yzf.service;

import com.yzf.vo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TODO
 * author: wangjian (jonath@163.com)
 * date: 2021/5/18 21:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceTest.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findByIdTest(){
        User user = userService.findById(1);
        System.out.println(user);

        System.out.println(userService.findById(1));
    }

}