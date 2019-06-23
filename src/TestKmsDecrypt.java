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


public class TestKmsDecrypt {

    public static void test(KMS_SIWEI kms_siwei, String para_kmsuser,String para_kmspassword,String para_name,String para_encryptPath,String para_decryptPath,int threadNum, int count) {

        new Thread(new TPSPrint(KmsDecrypt.class, "KmsDecrypt")).start();
        //启动停止线程
        new Thread(new StopTest(KmsDecrypt.class, TPSPrint.class)).start();
        for (int i = 0; i < threadNum; i++) {
            new Thread(new KmsDecrypt(kms_siwei, para_kmsuser, para_kmspassword, para_name,para_encryptPath,para_decryptPath, count)).start();
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
    public static class KmsDecrypt implements Runnable {
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
        public static String para_plainPath;
        public static String para_signPath;
        public static String para_name;
        public static String para_encryptPath;
        public static String para_decryptPath;
        public static String para_keyName;
        private String devIDHead;
        private String keyVersion;
        private ISDSCrypto vkekCrypto = null;

        public static void stop() {
            running = false;
        }

        public KmsDecrypt(KMS_SIWEI kms_siwei, String para_kmsuser,String para_kmspassword,String para_name,String para_encryptPath,String para_decryptPath, int count) {
            okCount = 0;
            failCount = 0;
            exCount = 0;
            running = true;
            this.count = count;
            this.kms_siwei = kms_siwei;
            this.para_kmsuser = para_kmsuser;
            this.para_kmspassword = para_kmspassword;
            this.para_keyName = para_keyName;
            this.para_algName = para_algName;
            this.para_plainPath = para_plainPath;
            this.para_signPath = para_signPath;
            this.para_encryptPath = para_encryptPath;
            this.para_decryptPath = para_decryptPath;
            this.para_name=para_name;

        }
        public void run() {
            int curCount = 0;
            String retJSONStr = null;
            JSONObject responseJson = null;
            String retCode = "";
            while ((running)) {
                try {
                    Result weitest5 = kms_siwei.getSymmKey(para_kmsuser, para_kmspassword, para_name, null);
                    Result result = kms_siwei.decrypt(para_encryptPath, para_decryptPath, (String) weitest5.getData());
                    if ((null!=result)&&(result.getCode() == 0)&&(null!=result.getData())) {
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
