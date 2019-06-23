import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2019/6/22 15:07
 */
public class test {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i <1; i++) {
            executorService.submit(() -> {
                TestPackingTools.test(100,100);
                latch.countDown();
            });
        }
        latch.await();
    }
}
