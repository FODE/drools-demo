package com.hcroad.demo.drools.entity;

/**
 * Created by Administrator on 2016/3/30 0030.
 */
public class User {
    private String name;
    private int birthMonth;
    private long score = 0L;
    private String message;

    public User(){}
    public User(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", birthMonth=" + birthMonth +
                ", score=" + score +
                ", message='" + message + '\'' +
                '}';
    }
}
