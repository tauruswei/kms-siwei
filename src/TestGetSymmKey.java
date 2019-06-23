/**
 * @Author: WeiBingtao/13156050650@163.com
 * @Version: 1.0
 * @Description:
 * @Date: 2019/6/21 18:16
 */

import com.alibaba.fastjson.JSONObject;
import com.sansec.device.crypto.ISDSCrypto;
import com.sansec.kms.result.Result;
import com.sansec.kms.service.impl.KMS_SIWEI;
import com.sansec.kms.service.impl.Util;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;


public class TestGetSymmKey {

    public static void test(KMS_SIWEI kms_siwei, String para_kmsuser, String para_kmspassword, String para_name, String para_cert_path, int threadNum, int count) {

        new Thread(new TPSPrint(GetSymmKey.class, "GetSymmKey")).start();
        //启动停止线程
        new Thread(new StopTest(GetSymmKey.class, TPSPrint.class)).start();
        for (int i = 0; i < threadNum; i++) {

            new Thread(new GetSymmKey(kms_siwei, para_kmsuser, para_kmspassword, para_name, para_cert_path, count)).start();
        }

    }

//    public static void testMultiQueryVKEK(ISDSCrypto   vkekCrypto) {
//        //int threadnum = InUtil.createThreadNumber();
////        int threadnum = 0;
//        while ((threadnum<1)||(threadnum>100) ){
//            System.out.println("输入线程号的范围是1-10.");
//            threadnum =	InUtil.createThreadNumber();
//        }
////
////
////        //String  devIDHead = InUtil.getString("请输入DevID的前16位字符，测试程序负责循环产生后4位字符，范围：00000->9999.  ", "112233445566779");
////        String  devIDHead = InUtil.getString("请输入DevID的前15位字符，线程号是第16位字符，测试程序负责循环产生后4位字符，范围：00000->9999.  ", "112233445566779");
////        String  keyVersion = InUtil.getString("请输入密钥版本号，需要确保输入的版本号的密钥在SecKMS内是存在的.  ", "2018-09-15T055201");
//        //启动打印线程
//        new Thread(new TPSPrint(GetSymmKey.class, "GetSymmKey")).start();
//        //启动停止线程
//        new Thread(new StopTest(GetSymmKey.class, TPSPrint.class)).start();
//        for(int i=0; i<threadnum; i++) {
//            new Thread(new QueryVKEK(devIDHead+i, keyVersion, vkekCrypto)).start();
//        }
//    }


    /**
     * 查询VKEK密钥
     *
     * @author
     */
    public static class GetSymmKey implements Runnable {
        public static int okCount = 0;
        public static int failCount = 0;
        public static int exCount = 0;
        public static boolean running;        // 运行标志
        public static int count = 0;
        public static KMS_SIWEI kms_siwei;
        public static String para_kmsuser;
        public static String para_kmspassword;
        public static String para_cert_path;
        public static String para_algName;
        public static String para_plainData;
        public static String para_signPath;
        public static String para_name;
        public static String para_encryptPath;
        public static String para_decryptPath;
        private String devIDHead;
        private String keyVersion;
        private ISDSCrypto vkekCrypto = null;

        public static void stop() {
            running = false;
        }

        public GetSymmKey(KMS_SIWEI kms_siwei, String para_kmsuser, String para_kmspassword, String para_name, String para_cert_path, int count) {
            okCount = 0;
            failCount = 0;
            exCount = 0;
            running = true;
            this.count = count;
            this.kms_siwei = kms_siwei;
            this.para_kmsuser = para_kmsuser;
            this.para_kmspassword = para_kmspassword;
            this.para_name = para_name;
            this.para_cert_path = para_cert_path;

        }
        public void run() {
            int curCount = 0;
            String retJSONStr = null;
            JSONObject responseJson = null;
            String retCode = "";
            while (running) {
                try {
                    byte[] bytes = null;
                    Result symmKey = null;
                    if (StringUtils.isNotBlank(para_cert_path)) {
                        try {
//                            String path = BackupRSAUtil.class.getResource("/conf/BackupPublicKey").getPath();
                            bytes = Util.readFromFile(para_cert_path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        symmKey = kms_siwei.getSymmKey(para_kmsuser, para_kmspassword, para_name, new String(bytes));
                    } else {
                        symmKey = kms_siwei.getSymmKey(para_kmsuser, para_kmspassword, para_name, null);
                    }
                    if ((null!=symmKey)&&(symmKey.getCode() == 0)&&(null!=symmKey.getData())) {
                        okCount++;
                    } else {
                        failCount++;
                    }
                } catch (Exception e) {
                    exCount++;
                    e.printStackTrace();
                }
                curCount++;
//                if (curCount > count-1) {
//                    curCount = 0;
//                }
            }
//            System.out.println("GetSymmKey Thread ...stop. currentThread Id=" + Thread.currentThread().getId());
        }
    }

}

