package com.lujy.drools_learning.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;


/**
 * @author liaowenhui
 */
public class UUIDUtil {


    /**
     * 生成原始UUID
     *
     * @return
     */
    private static String UUIDString() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    /**
     * 生成格式化UUID
     *
     * @return
     */
    public static String UUIDFormatString(String replace) {
        String uuid = UUID.randomUUID().toString().replaceAll(replace, "");
        return uuid;
    }

    /**
     * 值加密
     *
     * @return
     */
    public static String MD5AndUUID() {
        //时间戳
        String timeJab = String.valueOf(System.currentTimeMillis());
        //UUID+时间戳
        String concat = UUIDString().concat(timeJab);
        return DigestUtils.md5Hex(concat);
    }

    /**
     * 生成不重复的优惠券编码
     *
     * @return
     */
    public static String typeJoinTime() {
        String dateNowStr = StringJointUtil.dateToStringThree(new Date());
        int math = (int) ((Math.random() * 9 + 1) * 1000000);
        return dateNowStr.concat(Integer.toString(math));

    }

    /**
     * 使用StringTemplate模板拼接规则内容
     *
     * @param requestParam
     * @return
     */
    public static String rule(Map<String, Object> requestParam) {
        return ruleWordExchangsST(requestParam);
    }

    /**
     * 使用StringTemplate模板生成规则业务
     * 请求参数json示例：{"condition":{},"rule":{"name":"78元优惠券"},"action":{"money":78.0}}
     */
    public static String ruleWordExchangsST(Map<String, Object> requestParam) {
//        STGroup group = new STGroupString(WORK_MONEY_ST);
        //通过模板文件读取
        URL resource = UUIDUtil.class.getClassLoader().getResource("templates/drools/coupons_rules.stg");
        STGroup group = new STGroupFile(Objects.requireNonNull(resource).getFile());
        ST stFile = group.getInstanceOf("wordImport");
        ST stRule = group.getInstanceOf("ruleValue");

        Object condition = requestParam.get("condition");
        Object action = requestParam.get("action");
        Object rule = requestParam.get("rule");
        stRule.add("condition", condition);
        stRule.add("action", action);
        stRule.add("rule", rule);
        stFile.add("rules", stRule);
//        return stFile.render();
//        System.out.println(stRule.render());
//        System.out.println(stFile.render());
        return stFile.render();
    }
}
