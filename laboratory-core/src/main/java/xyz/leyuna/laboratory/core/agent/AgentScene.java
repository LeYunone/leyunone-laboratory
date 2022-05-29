package xyz.leyuna.laboratory.core.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-25
 */
public class AgentScene {

    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("方法一");
        System.out.println(agentOps);
    }

    public static void premain(String agentOps) {
        System.out.println("方法二");
        System.out.println(agentOps);
    }

    public static void agentmain(String agentOps, Instrumentation inst) throws UnmodifiableClassException, ClassNotFoundException {
        System.out.println("main方法之后执行");
        String className = "指定替换的类名";
        Class[] allLoadedClasses = inst.getAllLoadedClasses();
        Class tempClazz = null;
        for (Class clazz : allLoadedClasses) {
            if (className.equals(clazz.getCanonicalName())) {
                //需要替换类的类字节码文件
                tempClazz = clazz;
            }
        }
        ClassDefinition classDefinition = new ClassDefinition(tempClazz,"替换的类字节码的二进制数".getBytes());
        //替换
        inst.redefineClasses(classDefinition);
    }

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        //如果本虚拟机线程id为1，则监听他
        VirtualMachine attach = VirtualMachine.attach("1");
        //注入agentmain的探针系统
        attach.loadAgent("探针jar包的路径", "ops参数");
        System.out.println("main");
    }
}
