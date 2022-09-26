package com.lujy.drools_learning.utils;

/**
 * describe: 此类是一个StringTemplate模板工具类，通过传参数即可生成规则内容，
 * 将规则内容存放在数据库或者生产规则文件，为规则库的构建设定基础。
 * @author liaowenhui
 */
public class LossMoneyTemplate {
    public static final String WORK_MONEY_ST = "wordImport(rules) ::=<<\n" +
            "package com.promote\n" +
            "\n" +
            "import\tcom.droolsboot.model.RuleResult;\n" +
            "<rules; separator=\"\\n\\n\">\n" +
            ">>\n" +
            "\n" +
            "ruleValue(condition,action,rule) ::=<<\n" +
            "rule \"<rule.name>\"\n" +
            "\tno-loop true\n" +
            "\t\twhen\n" +
            "\t\t    $r:RuleResult(true)\n" +
            "\t\tthen\n" +
            "           modify($r){\n" +
            "                setPromoteName(drools.getRule().getName())<if(action)>,\n" +
            "                setFinallyMoney($r.getMoneySum() - <action.money><endif>)\n" +
            "           }\n" +
            "end\n" +
            ">>\n";
}
