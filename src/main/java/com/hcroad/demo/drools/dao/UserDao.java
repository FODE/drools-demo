package com.hcroad.demo.drools.dao;

import com.hcroad.demo.drools.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Administrator on 2016/4/1 0001.
 */
@Repository
public class UserDao {
    private static ConcurrentMap<Integer, User> USER_TABLE = new ConcurrentHashMap<Integer, User>();

    static {
        User user = new User(1, "sanglei", 100);
        USER_TABLE.put(user.getId(), user);
        user = new User(2, "xiaofei", 1000);
        USER_TABLE.put(user.getId(), user);
        user = new User(3, "xiaoming", 102);
        USER_TABLE.put(user.getId(), user);
        user = new User(4, "xiaogang", 45);
        USER_TABLE.put(user.getId(), user);
        user = new User(5, "xiaobai", 460);
        USER_TABLE.put(user.getId(), user);
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        for(Map.Entry<Integer, User> item : USER_TABLE.entrySet()) {
            users.add(item.getValue());
        }
        return users;
    }

    public User updateCost(User user) {
        if(USER_TABLE.get(user.getId()) == null) {
            return null;
        }
        User u = USER_TABLE.get(user.getId());
        u.setCost(user.getCost());
        USER_TABLE.put(u.getId(), u);
        return u;
    }


}
