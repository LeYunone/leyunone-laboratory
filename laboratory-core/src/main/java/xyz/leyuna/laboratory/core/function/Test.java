package xyz.leyuna.laboratory.core.function;

import org.springframework.beans.factory.annotation.Autowired;
import xyz.leyuna.laboratory.core.bean.Person;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author LeYuna
 * @email 365627310@qq.com
 * @date 2022-09-05
 */
public class Test {

    public static void main(String[] args) throws Exception {

        //比较函数
//        Integer[] arr = {1, 3, 5, 2, 4, 7, 6};
//        mainTest(arr, (t1, t2) -> t1 < t2);
//        for(Integer i : arr) System.out.print(i);
        //比较函数2
//        Person[] ps = {new Person(1),new Person(4),new Person(3),new Person(2),new Person(6)};
//        mainTest(ps,(t1,t2) -> t1.getAge() < t2.getAge());
//        mainTest(ps,new CompareFunction<Person>(){
//            @Override
//            public boolean compare(Person t1, Person t2) {
//                return t1.getAge() < t2.getAge();
//            }
//        });
//        mainTest(ps,CompareFunction::compare2);
//
//        for(Person p : ps) System.out.println(p.getAge());

//        //校验函数
//        mainTest2("test",str -> str.equals("tes2t"));

        //模板函数
//        mainTest3("test3-1",()->rpcTest.rpcTest());
//        mainTest3("test3-2",()->rpcTest.rpcTest2());

//        //规则函数
//        List<Person> persons = getPerson();
//        mainTest4(persons,person ->
//             person.getId()+person.getAge()
//        );
    }
    @Autowired
    private static RpcTest rpcTest;

    public static <T> void mainTest(T[] arr, CompareFunction<T> compareFunction) {
        for (int i = 0; i < arr.length; i++) {
            boolean is = true;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (compareFunction.compare(arr[j + 1], arr[j])) {
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    is = false;
                }
            }
            if (is) {
                break;
            }
        }
    }

    public static void mainTest2(String name, CheckFunction checkFunction) {
        if (checkFunction != null && checkFunction.check(name)) {
            System.out.println("校验成功success");
        } else {
            System.out.println("校验失败fail");
        }
    }

    public static void mainTest3(String name, Runnable methodFunction) {
        System.out.println("增强方法:"+name);
        methodFunction.run();
        System.out.println("增强方法成功");
    }

    public static void mainTest3(String name, Callable methodFunction) throws Exception {
        System.out.println("增强方法:"+name);
        Object call = methodFunction.call();
        System.out.println("返回:"+call);
        System.out.println("增强方法成功");
    }

    public static <T, R>void mainTest4(List<T> ls, UniqueFunction<T, R> uniqueFunction) {
        Map map = new LinkedHashMap<>();
        for (T t : ls) {
            R rule = uniqueFunction.getRule(t);
            if (!map.containsKey(rule)) {
                map.put(rule, t);
            }
        }
        ls = new ArrayList<>(map.values());
    }


    public static List<Person> getPerson() {
        List<Person> people = new ArrayList<>();
        people.add(new Person("1", "1", 1));
        people.add(new Person("1", "2", 2));
        people.add(new Person("2", "3", 1));
        people.add(new Person("2", "4", 2));
        people.add(new Person("3", "5", 1));
        people.add(new Person("3", "6", 2));
        people.add(new Person("4", "7", 1));
        people.add(new Person("1", "8", 2));
        people.add(new Person("2", "9", 2));
        return people;
    }

}
