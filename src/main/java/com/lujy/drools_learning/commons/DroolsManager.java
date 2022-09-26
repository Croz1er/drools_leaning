package com.lujy.drools_learning.commons;

import com.lujy.drools_learning.entity.DroolsRule;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kie.builder.impl.KieContainerImpl;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.runtime.Globals;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @Author: junyu.lu
 * @Date: 2022/9/23 8:51
 */
@Component
public class DroolsManager {

    private static final Logger log = LoggerFactory.getLogger(DroolsManager.class);
    //此类本身就是单例
    private static final KieServices kieServices = KieServices.get();
    //kie文件系统，需要缓存，如果每次添加规则都是重新new一个的话，则可能出现问题
    private static final KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
    //可以理解为构建kmodule.xml
    private static final KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
    //需要全局唯一一个，如果每次价格规则都新创建一个，那么就需要销毁之前创建的看kiecontainer
    private volatile static KieContainer kieContainer;

    /**
     * 创建kieContainer
     */
    private void createKieContainer() {
        synchronized (KieContainer.class) {
            if (kieContainer == null) {
                kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
            }
        }

    }

    /**
     * 判定kbase
     *
     * @param kieBaseName kie基本名称
     * @return
     */
    private boolean existsKieBase(String kieBaseName) {
        if (null == kieContainer) return false;
        Collection<String> kieBaseNames = kieContainer.getKieBaseNames();
        if (kieBaseNames.contains(kieBaseName)) return true;
        log.warn("需要创建KieBase:{}", kieBaseName);

        return false;
    }
    /**
     * 删除规则
     *
     * @param kieBaseName
     * @param packageName
     * @param ruleName
     */
    public void deleteDroolsRule(String kieBaseName, String packageName, String ruleName) {
        if (kieContainer == null) {
            throw new RuntimeException("kieContainer 暂未保存任何规则,请先添加规则");
        }
        if (existsKieBase(kieBaseName)) {
            KieBase kieBase = kieContainer.getKieBase(kieBaseName);
            kieBase.removeRule(packageName, ruleName);
            log.info("删除kieBase:[{}]包:[{}]下的规则:[{}]", kieBaseName, packageName, ruleName);
        }
    }

    /**
     * 添加或更新 drools规则
     *
     * @param droolsRule
     */
    public void addOrUpdateRule(DroolsRule droolsRule) {
        //获取kbase的名称
        String kieBaseName = droolsRule.getKieBaseName();
        //获取kbase包的名称
        String kiePackageName = droolsRule.getKiePackageName();
        //判断给kbase是否存在
        boolean existsKieBase = existsKieBase(kiePackageName);
        //该对象对应module.xml 中的kbase标签
        KieBaseModel kieBaseModel = null;
        //如果这个kbase不存在
        if (!existsKieBase) {
            //创建一个kBase 根据一个kiBaseName创建一个kieBaseModel
            log.info("first create kieBaseName");
            kieBaseModel = kieModuleModel.newKieBaseModel(kieBaseName);
            //不是默认的kiebase
            kieBaseModel.setDefault(false);
            //设置该KieBase需要加载的包路径
            kieBaseModel.addPackage(kiePackageName);
            //设置kie的session
            kieBaseModel.newKieSessionModel(kieBaseName + "-session")
                    //不是默认session
                    .setDefault(false);

        } else {
            //获取到已经存在的base对象
            kieBaseModel = kieModuleModel.getKieBaseModels().get(kieBaseName);
            //获取package
            List<String> packages = kieBaseModel.getPackages();
            //判定这个包是否已经存在
            if (!packages.contains(kiePackageName)) {
                kieBaseModel.addPackage(kiePackageName);
                log.info("kieBase:{}添加一个新的包:{}", kieBaseName, droolsRule.getKiePackageName());
            } else {
                kieBaseModel = null;
            }
        }

        String file = DroolCommon.DROOL_PACKAGE_PREFIX + kiePackageName + DroolCommon.DROOL_PACKAGE_I + kieBaseName + DroolCommon.DROOL_PACKAGE_SUFFIX;
//        String file = "src/main/resources/" + droolsRule.getKiePackageName() + "/" + droolsRule.getRuleId() + ".drl";
        System.out.println(file);
        log.info("加载虚拟规则文件：{}", file);
        //这里的rule规则是手动输入的
        // TODO: 2022/9/26 尝试修改为只输入lsh和rsh 然后根据模板文件生成一个对应的虚拟dlr文件 来进行修改或者添加 可行性?
        String ruleContent = droolsRule.getRuleContent();
        kieFileSystem.write(file, ruleContent);

        if (kieBaseModel != null) {
            String kmoduleXml = kieModuleModel.toXML();
            log.info("加载kmodule.xml:[\n{}]", kmoduleXml);
            kieFileSystem.writeKModuleXML(kmoduleXml);
        }


        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        //通过KieBuilder构建KieModule下所有的KieBase
        kieBuilder.buildAll();
        //获取构建过程中的结果
        Results results = kieBuilder.getResults();
        //获取构建过程中的结果

        List<Message> messages = results.getMessages(Message.Level.ERROR);
        if (null != messages && !messages.isEmpty()) {
            for (Message message : messages) {
                log.error(message.getText());
            }
            throw new RuntimeException("加载规则出现异常");
        }
        //keiContainer只有第一次时才需要创建,之后就是使用这个
        if (null == kieContainer) {
            log.info("first create kieContainer");
            createKieContainer();
        } else {
            //实现动态更新
            log.info("update kieContainer");
            ((KieContainerImpl) kieContainer).updateToKieModule((InternalKieModule) kieBuilder.getKieModule());
        }
    }

    /**
     * 触发规则，此处简单模拟，会向规则中插入一个Integer类型的值
     */
    public String fireRule(String kieBaseName, Integer param) {
        // 创建kieSession
        KieSession kieSession = kieContainer.newKieSession(kieBaseName + "-session");
        StringBuilder resultInfo = new StringBuilder();
        Globals globals = kieSession.getGlobals();
        System.out.println(globals);
        kieSession.setGlobal("resultInfo", resultInfo);
        kieSession.insert(param);
        kieSession.fireAllRules();
        kieSession.dispose();
        String result = resultInfo.toString();
        System.out.println("result:" + result);
        return result;
    }

}
