package com.lujy.drools_learning.controller;

import com.lujy.drools_learning.commons.DroolsManager;
import com.lujy.drools_learning.entity.DroolsRule;
import com.lujy.drools_learning.service.DroolsRuleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: junyu.lu
 * @Date: 2022/9/23 14:57
 */
@RestController
@RequestMapping("/drools/rule")
public class DroolsRuleController {

    @Resource
    private DroolsRuleService droolsRuleService;

    @Resource
    private DroolsManager droolsManager;

    @GetMapping("findAll")
    public List<DroolsRule> findAll() {
        return droolsRuleService.findAll();
    }

    @PostMapping("add")
    public String addRule(@RequestBody DroolsRule droolsRule) {
        droolsRuleService.addDroolsRule(droolsRule);
        return "添加成功";
    }

    @PostMapping("update")
    public String updateRule(@RequestBody DroolsRule droolsRule) {
        droolsRuleService.updateDroolsRule(droolsRule);
        return "修改成功";
    }

    @PostMapping("deleteRule")
    public String deleteRule(Long ruleId, String ruleName) {
        droolsRuleService.deleteDroolsRule(ruleId, ruleName);
        return "删除成功";
    }

    @GetMapping("fireRule")
    public String fireRule(@RequestParam String kieBaseName, @RequestParam Integer param) {
        System.out.println(kieBaseName+"-"+param);
        return droolsManager.fireRule(kieBaseName, param);
    }

}
