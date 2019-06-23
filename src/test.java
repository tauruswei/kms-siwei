import com.alibaba.fastjson.JSON;
import com.sansec.kmspackage.result.Result;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2019/6/22 15:07
 */
public class test {

    static  int successCount = 0;
    static  int failCount = 0;

    public static void main(String[] args) throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(1);
//        CountDownLatch latch = new CountDownLatch(1);
//        for (int i = 0; i <1; i++) {
//            executorService.submit(() -> {
//                TestPackingTools.test(100,100);
//                latch.countDown();
//            });
//        }
//        latch.await();
        long startTime = System.currentTimeMillis();
        test1();
        long endTime = System.currentTimeMillis();
        System.out.println("成功次數："+ successCount);
        String msg = Util.sdf.format(new Date()) + "\t\t\t"
                + ("成功次數："+ successCount) + "\t\t\t"
                + ("失敗次數："+failCount)+"\t\t\t"
                + ("TPS:"+successCount/1.0/(endTime-startTime)*1000)+"\t\t\t\t";
        System.out.println(msg);
    }
    public static void test1() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = (ExecutorService) Executors.newFixedThreadPool(100);
        List<Future<String>> resultList = new ArrayList<Future<String>>();

        // 创建10个任务并执行
        for (int i = 0; i < 10000; i++) {
            // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
            Future<String> future = executorService.submit(new TaskWithResult(i));
            // 将任务执行结果存储到List中
            resultList.add(future);
        }
        executorService.shutdown();

        // 遍历任务的结果
        for (Future<String> fs : resultList) {
            try {
                System.out.println(fs.get().toString()); // 打印各个线程（任务）执行的结果
                Result jsonObject = JSON.parseObject(fs.get().toString(), Result.class);
                if (jsonObject.getCode()==0){
                    successCount++;
                }else {
                    failCount++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
//                executorService.shutdownNow();
                e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        String msg = Util.sdf.format(new Date()) + "\t\t\t"
                + ("成功次數："+ successCount) + "\t\t\t"
                + ("失敗次數："+failCount)+"\t\t\t"
                + ("TPS:"+successCount/1.0/(endTime-startTime)*1000)+"\t\t\t\t";
        System.out.println(msg);
    }
}

