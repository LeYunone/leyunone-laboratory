package xyz.leyuna.laboratory.core.function;

import com.sun.tools.javac.comp.Check;
import xyz.leyuna.laboratory.core.bean.Person;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-05
 */
public class Test {

    public static void main(String[] args) {
        //校验
//        mainTest("test",str -> str.equals("tes2t"));

        //模板方法
//        mainTest2("test2",(t,r)->{
//            System.out.println(t);
//            System.out.println(r);
//        });

        //自定义规则
//        List<Person> persons = getPerson();
//        mainTest3(persons,person ->
//             person.getId()+person.getAge()
//        );

    }

    public static void mainTest(String name, CheckFunction checkFunction){
        if(checkFunction!=null && checkFunction.check(name)){
            System.out.println("boolean");
        }else{
            System.out.println("no boolean");
        }
    }

    public static void mainTest2(String name,MethodFunction methodFunction){
        System.out.println("增强方法");
        methodFunction.method(name,"r执行");
        System.out.println("增强方法成功");
    }

    public static<T,R> void mainTest3(List<T> personList, UniqueFunction<T,R> uniqueFunction){
        Map map = new LinkedHashMap<>();
        for(T t : personList){
            R rule = uniqueFunction.getRule(t);
            if(!map.containsKey(rule)){
                map.put(rule,t);
            }
        }
        personList = new ArrayList<>(map.values());
    }

    public static List<Person> getPerson(){
        List<Person> people = new ArrayList<>();
        people.add(new Person("1","1",1));
        people.add(new Person("1","2",2));
        people.add(new Person("2","3",1));
        people.add(new Person("2","4",2));
        people.add(new Person("3","5",1));
        people.add(new Person("3","6",2));
        people.add(new Person("4","7",1));
        people.add(new Person("1","8",2));
        people.add(new Person("2","9",2));
        return people;
    }
}
