package xyz.leyuna.laboratory.core.agent;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class TopInterceptor {
    @RuntimeType
    public static Object interceptor(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
        System.err.println("执行方法："+method.getName());
        // 拦截操作
        return callable.call();

    }
}
