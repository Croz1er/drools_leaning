package com.lujy.drools_learning;

import com.lujy.drools_learning.utils.LossMoneyTemplate;
import com.lujy.drools_learning.utils.UUIDUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
class DroolsLearningApplicationTests {

    @Test
    void contextLoads() {
        String file = this.getClass().getClassLoader().getResource("templates/drools").getFile();
        File file1 = new File(file);
        System.out.println(file1);
    }

    @Test
    void StringTemplateTest() {
        Map<String, Object> map = new HashMap<>();
        //组合Rule部分
        Map<String, Object> rule = new HashMap<>();
        rule.put("name", "rule01");

        map.put("rule", rule);
        //组合 规则When部分
        Map<String, Object> when = new HashMap<>();
        map.put("condition", when);
        //组合 规则Then部分
        Map<String, Object> then = new HashMap<>();
        then.put("money", "23.99");

        map.put("action", then);
//        System.out.println(map);
        String wordExchangsST = UUIDUtil.ruleWordExchangsST(map);
//        System.out.println(wordExchangsST);
//        System.out.println(LossMoneyTemplate.WORK_MONEY_ST);
    }

}
