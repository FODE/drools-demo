package com.hcroad.demo.drools.service;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Administrator on 2016/4/5 0005.
 */
@Service
public class DroolsService {
    private ConcurrentMap<String, Object> beans = new ConcurrentHashMap<String, Object>();

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void postConstruct() {
        // 加载dao和service bean，方便下面设置drools的全局变量
        String[] beanNames = context.getBeanDefinitionNames();
        for(String name : beanNames) {
            if(name.endsWith("Dao") || name.endsWith("Service")) {
                beans.put(name, context.getBean(name));
            }
        }

        // 设置drools文件的全局变量信息,在文件的最后追加 global声明
        Resource resource = context.getResource("classpath:drools/rules/rule.drl");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(resource.getFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            if(beans != null && beans.size() > 0) {
                bw.write("package com.hcroad.demo.rules\n\n");
                bw.write("import com.hcroad.demo.drools.entity.*;\n");
                bw.write("import com.hcroad.demo.drools.dao.*;\n");
                bw.write("import com.hcroad.demo.drools.service.*;\n\n");
                for(Map.Entry<String, Object> bean : beans.entrySet()) {
                    String type = bean.getKey().substring(0, 1).toUpperCase();
                    String text = "global " + type + bean.getKey().substring(1) + " " + bean.getKey() + ";\n";
                    System.out.println(text);
                    bw.write(text);
                }
                bw.write("\n");
                bw.close();
            }
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

    public void executeRule() {
        // 加载规则
        KieServices kieService = KieServices.Factory.get();
        KieContainer kieContainer = kieService.newKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession");

        // 设置全局变量
        if(beans != null && beans.size() > 0) {
            for(Map.Entry<String, Object> bean : beans.entrySet()) {
                kieSession.setGlobal(bean.getKey(), bean.getValue());
            }
        }

        // 触发规则
        kieSession.fireAllRules();

        // 释放session
        kieSession.dispose();
    }

}
