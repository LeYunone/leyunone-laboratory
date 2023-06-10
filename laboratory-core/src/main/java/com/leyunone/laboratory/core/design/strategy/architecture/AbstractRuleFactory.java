package com.leyunone.laboratory.core.design.strategy.architecture;

/**
 * :)
 *
 * @Author LeYunone
 * @Date 2023/6/10 17:10
 */
public abstract class AbstractRuleFactory {

    public abstract void register(String identif, AbstractRule handler);

    public abstract AbstractRule getHandler(String identif);
}
