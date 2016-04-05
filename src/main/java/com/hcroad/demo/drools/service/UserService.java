package com.hcroad.demo.drools.service;

import com.hcroad.demo.drools.dao.UserDao;
import com.hcroad.demo.drools.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ApplicationContext context;

    private String user = "sanglei";
    private String pass = "123";

    public void checkLogin(String username, String password) {
        if(username == null || username.trim().equals("") || !username.trim().equals(user)) {
            throw new RuntimeException("unknown user name!");
        }
        if(password == null || password.trim().equals("") || !password.trim().equals(pass)) {
            throw new RuntimeException("increct password!");
        }
        log(username + " login.");
    }

    public User updateUser(Integer id, Integer cost) {
        User user = new User();
        user.setId(id);
        user.setCost(cost);
        return userDao.updateCost(user);
    }

    private void log(String msg) {
        Resource resource = context.getResource("classpath:log.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(resource.getFile(), true);
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(msg);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
