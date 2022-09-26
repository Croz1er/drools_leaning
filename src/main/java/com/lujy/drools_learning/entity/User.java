package com.lujy.drools_learning.entity;

import java.util.List;

/**
 * @Author: junyu.lu
 * @Date: 2022/9/26 10:22
 */
public class User {

    private Integer id;
    private String name;
    private Integer age;
    private Integer sex; //0 female 1male

    private List<Account> accounts;

    public User() {
    }

    public User(Integer id, String name, Integer age, Integer sex, List<Account> accounts) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.accounts = accounts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
