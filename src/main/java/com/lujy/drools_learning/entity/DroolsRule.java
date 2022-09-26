package com.lujy.drools_learning.entity;

import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @Author: junyu.lu
 * @Date: 2022/9/23 8:35
 */
public class DroolsRule {

    /**
     * 规则id --- 后期不用自行添加 根据数据库自动生成
     */
    private Long ruleId;
    /**
     * kbase的名字
     */
    private String kieBaseName;
    /**
     * 设置该kbase需要从那个目录下加载文件，
     * 虚拟目录
     */
    private String kiePackageName;
    /**
     * 规则内容
     */
    private String ruleContent;
    /**
     * 规则创建时间
     */
    private Date createdTime;
    /**
     * 规则更新时间
     */
    private Date upDateTime;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public String getKieBaseName() {
        return kieBaseName;
    }

    public void setKieBaseName(String kieBaseName) {
        this.kieBaseName = kieBaseName;
    }

    public String getKiePackageName() {
        return kiePackageName;
    }

    public void setKiePackageName(String kiePackageName) {
        this.kiePackageName = kiePackageName;
    }

    public String getRuleContent() {
        return ruleContent;
    }

    public void setRuleContent(String ruleContent) {
        this.ruleContent = ruleContent;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpDateTime() {
        return upDateTime;
    }

    public void setUpDateTime(Date upDateTime) {
        this.upDateTime = upDateTime;
    }

    public void validate() {
        if (this.ruleId == null || isBlank(kieBaseName) || isBlank(kiePackageName) || isBlank(ruleContent)) {
            throw new RuntimeException("参数有问题");
        }
    }

    private Boolean isBlank(String str) {
        return str == null || str.isEmpty();
    }

}
