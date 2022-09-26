package com.lujy.drools_learning.entity;

/**
 * @Author: junyu.lu
 * @Date: 2022/9/26 16:59
 * 用户账户
 */
public class Account {

    private Integer id;
    private String cardNum;
    private Long money;

    public Account() {
    }

    public Account(Integer id, String cardNum, Long money) {
        this.id = id;
        this.cardNum = cardNum;
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

}
