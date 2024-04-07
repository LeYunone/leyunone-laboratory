package com.leyunone.laboratory.core.rule;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.core.RuleBuilder;

/**
 * :)
 *
 * @author LeYunone
 * @email 365627310@qq.com
 * @date 2024/4/8
 */
public class EasyRuleTest {

    public static void main(String[] args) {
//        Rules rules = new Rules();
//        rules.register(new RuleBuilder()
//                .when((facts -> facts.get("mess").equals("A")))
//                .then(facts -> {
//                    System.out.println("更新");
//                })
//                .build());
//        RulesEngine rulesEngine = new DefaultRulesEngine();
//        Facts facts = new Facts();
//        facts.put("mess","A");
//        rulesEngine.fire(rules,facts);

        AllEstablishRule allEstablishRule = new AllEstablishRule(CollectionUtil.newArrayList(new RuleBuilder()
                .when((facts -> facts.get("mess").equals("A")))
                .build(),new RuleBuilder()
                .when((facts -> facts.get("mess").equals("B")))
                .build()),()->{
            System.out.println("更新");
        });
        Facts facts = new Facts();
        facts.put("mess","A");
        allEstablishRule.execute(facts);
    }
}
