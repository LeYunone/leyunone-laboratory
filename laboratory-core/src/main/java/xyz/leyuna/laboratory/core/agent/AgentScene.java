package xyz.leyuna.laboratory.core.agent;


import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-05-25
 */
public class AgentScene {

    //AOP
    public static void premain(String agentOps, Instrumentation inst) {
        System.out.println("方法一");
        System.out.println(agentOps);
//
//        AopClass aopClass = new AopClass();
//        inst.addTransformer(aopClass,true);
    }

    public static void premain(String agentOps) {
        System.out.println("方法二");
        System.out.println(agentOps);
    }

//    //顶级拦截器
//    public static void premain(String args, Instrumentation inst){
//        System.err.println("开始拦截："+args);
//        AgentBuilder.Transformer transformer=new AgentBuilder.Transformer() {
//            @Override
//            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
//                // method对所有方法进行拦截
//                // intercept添加拦截器
//                return builder.method(ElementMatchers.<MethodDescription>any())
//                        .intercept(MethodDelegation.to(TopInterceptor.class));
//            }
//        };
//        // 指定拦截
//        String prefix = "xyz.leyuna.laboratory.core.agent";
//        new AgentBuilder.Default().type(ElementMatchers.<TypeDescription>nameStartsWith(prefix))
//                .transform(transformer).installOn(inst);
//    }

    //热部署
//    public static void agentmain(String agentOps, Instrumentation inst) throws Exception {
//        System.out.println("main方法之后执行");
//        String className = "xyz.leyuna.laboratory.core.agent.HotClass";
//        Class[] allLoadedClasses = inst.getAllLoadedClasses();
//        Class tempClazz = null;
//        for (Class clazz : allLoadedClasses) {
//            if (className.equals(clazz.getCanonicalName())) {
//                //需要替换类的类字节码文件
//                tempClazz = clazz;
//            }
//        }
//        String path = "E:\\TheCore\\leyuna-laboratory\\laboratory-core/HotClass.class";
//        File file = new File(path);
//        FileInputStream in = new FileInputStream(file);
//        byte[] bytes = new byte[in.available()];
//        in.read(bytes);
//        in.close();
//
//        ClassDefinition classDefinition = new ClassDefinition(tempClazz,bytes);
//        //替换
//        inst.redefineClasses(classDefinition);
//
//        new HotClass().hot();
//    }

//    public static void agentmain(String agentOps, Instrumentation inst) throws Exception {
//        System.out.println("main方法之后执行");
//    }

//-javaagent:E:\TheCore\leyuna-laboratory\laboratory-core\target\laboratory-core-0.0.1-SNAPSHOT.jar=test
    public static void main(String[] args) throws Exception {
//        MockService mockService = new MockService();
//        mockService.doService();
//
//        HotClass hotClass = new HotClass();
//        hotClass.hot();
//
//        Thread.sleep(5000L);
//        //获取当前系统中所有 运行中的 虚拟机
//        List<VirtualMachineDescriptor> list = VirtualMachine.list();
//        for (VirtualMachineDescriptor vmd : list) {
//            //然后加载 agent.jar 发送给该虚拟机
//            if(vmd.displayName().contains("AgentScene")){
//                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
//                virtualMachine.loadAgent("E:\\TheCore\\leyuna-laboratory\\laboratory-core\\target\\laboratory-core-0.0.1-SNAPSHOT.jar","ops参数");
//                virtualMachine.detach();
//            }
//        }
    }
}
