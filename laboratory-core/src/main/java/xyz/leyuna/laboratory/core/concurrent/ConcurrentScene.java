package xyz.leyuna.laboratory.core.concurrent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author pengli
 * @date 2022-04-17
 * 模拟并发场景
 */
public class ConcurrentScene {

    public static List<Future> result = new ArrayList();

    public static Integer count = 100;

    public static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        List<Callable> temp = new ArrayList<>();
        for(int i=0;i<=3;i++){
            final int j = i;
            temp.add(new Callable() {
                @Override
                public Object call() throws Exception {
                    while(true){
                        if(count>0){
                            synchronized (count){
                                count--;
                                System.out.println(j+"==="+count);
                            }
                        }else{
                            return j;
                        }
                    }

                }
            });
        }
        ConcurrentScene concurrentScene = new ConcurrentScene();
        concurrentScene.start(temp);

        Thread.sleep(100);
        ConcurrentScene.result.forEach(t->{
            try {
                System.out.print(t.get()+" ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    void start(List list) throws InterruptedException {
        new Thread(()->{
            ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 1000, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
            try {
                List<Future> list1 = executor.invokeAll(list);
                ConcurrentScene.result.addAll(list1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"开启线程").start();
    }
}
