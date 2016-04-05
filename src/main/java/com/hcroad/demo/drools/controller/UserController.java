package com.hcroad.demo.drools.controller;

import com.hcroad.demo.drools.entity.User;
import com.hcroad.demo.drools.service.RuleService;
import com.hcroad.demo.drools.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RuleService ruleService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @ResponseBody
    @RequestMapping("/login")
    public String login(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password) {
        try {
            userService.checkLogin(username, password);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "login success!";
    }

    @ResponseBody
    @RequestMapping("/rule/{type}")
    public List<User> rule(@PathVariable("type") String type) {
        if("spring".equals(type.trim().toLowerCase())) {
            return ruleService.executeSpringRule();
        } else if("drools".equals(type.trim().toLowerCase())) {
            return ruleService.executeRule();
        } else if("stateLess".equals(type.trim())) {
            return ruleService.executeRuleStateless();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/user/update/{id}/{cost}")
    public User updateUser(@PathVariable("id") Integer id, @PathVariable("cost") Integer cost) {
        return userService.updateUser(id, cost);
    }

}
