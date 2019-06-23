import com.alibaba.fastjson.JSON;
import com.sansec.kmspackage.result.CodeMsg;
import com.sansec.kmspackage.result.Result;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.Callable;

/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2019/6/23 0023 22:54
 */
class TaskWithResult implements Callable<String> {
    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    /**
     * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
     *
     * @return
     * @throws Exception
     */
    @Override
    public String call() throws Exception {
//        System.out.println("call()方法被自动调用,干活！！！" + Thread.currentThread().getName());
        if( RandomUtils.nextInt(0,10)>8){
            return JSON.toJSONString(Result.error(CodeMsg.UPLOAD_ERROR));
        }
        return JSON.toJSONString(Result.success(id));
    }
}
