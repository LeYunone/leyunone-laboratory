package xyz.leyuna.laboratory.core.agent;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-25
 */
public class AgentScene {

    public static void premain(String agentOps, Instrumentation inst){
        System.out.println("方法一");
        System.out.println(agentOps);
    }

    public static void premain(String agentOps){
        System.out.println("方法二");
        System.out.println(agentOps);
    }

    public static void agentmain(String agentOps,Instrumentation inst){
        System.out.println("main方法之后执行");
    }

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        //如果本虚拟机线程id为1，则监听他
        VirtualMachine attach = VirtualMachine.attach("1");
        attach.loadAgent("探针jar包的路径","ops参数");
        System.out.println("main");
    }
}
