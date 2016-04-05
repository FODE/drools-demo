package com.hcroad.demo.drools.service;

import com.hcroad.demo.drools.dao.UserDao;
import com.hcroad.demo.drools.entity.User;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
@Service
public class RuleService {
    @Autowired
    private UserDao userDao;

    private KieSession kSession;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void postConstruct(){
        kSession = (KieSession) context.getBean("ksession");

    }

    public List<User> executeRule() {
        List<User> users = userDao.findAll();
        if(users == null || users.size() <= 0) {
            return users;
        }

        // 执行规则
        KieServices services = KieServices.Factory.get();
        KieContainer kieContainer = services.newKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession");

        String[] names = context.getBeanDefinitionNames();
        for(String name : names) {
            if(name.endsWith("Dao") || name.endsWith("Service")) {
                System.out.println(name);
                System.out.println(context.getBean(name));
                kieSession.setGlobal(name, context.getBean(name));
            }
        }

        kieSession.fireAllRules();
        kieSession.dispose();
        return users;
    }

    public List<User> executeRuleStateless() {
        List<User> users = userDao.findAll();
        if(users == null || users.size() <= 0) {
            return users;
        }

        // 执行规则
        KieServices services = KieServices.Factory.get();
        KieContainer kieContainer = services.newKieClasspathContainer();
        StatelessKieSession kieSession = kieContainer.newStatelessKieSession("ksessionLess");

        kieSession.execute(users);

        return users;
    }

    public List<User> executeSpringRule() {
        kSession = (KieSession) context.getBean("ksession");
        List<User> users = userDao.findAll();
        if(users == null || users.size() <= 0) {
            return users;
        }

        // 执行规则

        for(User user : users) {
            kSession.insert(user);
        }
        kSession.fireAllRules();
        kSession.fireAllRules();
        return users;
    }

}
