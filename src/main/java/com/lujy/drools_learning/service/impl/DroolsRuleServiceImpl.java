package com.lujy.drools_learning.service.impl;

import com.lujy.drools_learning.commons.DroolsManager;
import com.lujy.drools_learning.entity.DroolsRule;
import com.lujy.drools_learning.service.DroolsRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: junyu.lu
 * @Date: 2022/9/23 14:26
 */
@Service
public class DroolsRuleServiceImpl implements DroolsRuleService {

    @Resource
    private DroolsManager droolsManager;

    @Override
    public List<DroolsRule> findAll() {
        return new ArrayList<>(droolsRuleMap.values());
    }

    @Override
    public void addDroolsRule(DroolsRule droolsRule) {
        droolsRule.validate();
        droolsRule.setCreatedTime(new Date());
        droolsRuleMap.put(droolsRule.getRuleId(),droolsRule);
        droolsManager.addOrUpdateRule(droolsRule);
    }

    @Override
    public void updateDroolsRule(DroolsRule droolsRule) {
        droolsRule.validate();
        droolsRule.setUpDateTime(new Date());
        droolsRuleMap.put(droolsRule.getRuleId(),droolsRule);
        droolsManager.addOrUpdateRule(droolsRule);
    }

    @Override
    public void deleteDroolsRule(Long ruleId, String ruleName) {
        DroolsRule droolsRule = droolsRuleMap.get(ruleId);
        if (null != droolsRule) {
            droolsRuleMap.remove(ruleId);
            droolsManager.deleteDroolsRule(droolsRule.getKieBaseName(), droolsRule.getKiePackageName(), ruleName);
        }
    }
}
