package com.hcroad.demo.drools.service;

import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
@Service
public class UserService {
    private String user = "sanglei";
    private String pass = "123";

    public void checkLogin(String username, String password) {
        if(username == null || username.trim().equals("") || !username.trim().equals(user)) {
            throw new RuntimeException("unknown user name!");
        }
        if(password == null || password.trim().equals("") || !password.trim().equals(pass)) {
            throw new RuntimeException("increct password!");
        }
    }

}
