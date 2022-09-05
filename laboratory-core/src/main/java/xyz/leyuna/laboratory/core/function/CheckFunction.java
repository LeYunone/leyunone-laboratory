package xyz.leyuna.laboratory.core.function;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-05
 */
@FunctionalInterface
public interface CheckFunction<T> {

    boolean check(T t);
}
