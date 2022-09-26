package com.lujy.drools_learning.service;

import com.lujy.drools_learning.entity.DroolsRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: junyu.lu
 * @Date: 2022/9/23 14:03
 */
public interface DroolsRuleService {

    //这里可以设置为对应的数据库
    Map<Long, DroolsRule> droolsRuleMap = new HashMap<>(16);

    List<DroolsRule> findAll();

    /**
     * 添加Drools规则
     * @param droolsRule
     */
    void addDroolsRule(DroolsRule droolsRule);

    /**
     * 更新规则
     * @param droolsRule
     */
    void updateDroolsRule(DroolsRule droolsRule);

    /**
     * 删除规则
     * @param ruleId
     * @param ruleName
     */
    void deleteDroolsRule(Long ruleId,String ruleName);
}
