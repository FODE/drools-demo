package com.hcroad.demo.drools.service;

import com.hcroad.demo.drools.entity.User;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
@Service
public class RuleService {
    private User user = new User("sanglei");

    private KieSession kSession;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void postConstruct(){
        kSession = (KieSession) context.getBean("ksession");
        System.out.println("kieSession == "+kSession);
    }

    public User executeRule() {

        KieServices services = KieServices.Factory.get();
        KieContainer kieContainer = services.newKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession");

        user.setBirthMonth(3);
        user.setMessage("");

        kieSession.insert(user);
        kieSession.fireAllRules();

        System.out.println(user.getMessage());

        return user;
    }

    public User executeSpringRule() {
        if(kSession == null) {
            user.setMessage("没加载到规则!");
            return user;
        }
        user.setMessage("");
        user.setBirthMonth(3);
        System.out.println(user);
        kSession.insert(user);
        kSession.fireAllRules();
        System.out.println(user);

        return user;
    }

}
