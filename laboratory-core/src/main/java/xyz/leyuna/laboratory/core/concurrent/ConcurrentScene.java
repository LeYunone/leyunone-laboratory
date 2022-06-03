package xyz.leyuna.laboratory.core.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author pengli
 * @date 2022-04-17
 * 模拟并发场景
 */
public class ConcurrentScene {

    public static List<Future> result = new ArrayList();

    public static void main(String[] args) throws InterruptedException {
        List<Callable> temp = new ArrayList<>();
        for(int i=0;i<=10;i++){
            final int j = i;
            temp.add(new Callable() {
                @Override
                public Object call() throws Exception {
                    System.out.println(j);
                    return j;
                }
            });
        }
        ConcurrentScene concurrentScene = new ConcurrentScene();
        concurrentScene.start(temp);

        Thread.sleep(1000);
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
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 1000, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        List<Future> list1 = executor.invokeAll(list);
        ConcurrentScene.result.addAll(list1);
    }
}
