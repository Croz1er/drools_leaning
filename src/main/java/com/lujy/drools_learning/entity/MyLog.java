package com.lujy.drools_learning.entity;

import java.util.Date;

public class MyLog {
    private Integer id;
    private String content;
    private Integer userId;
    private Integer cardId;
    private Date createDate;

    public MyLog() {
    }

    public MyLog(Integer id, String content, Integer userId, Integer cardId, Date createDate) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.cardId = cardId;
        this.createDate = createDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}