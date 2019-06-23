import com.alibaba.fastjson.JSON;
import com.sansec.kms.result.Result;
import com.sansec.kms.service.impl.KMS_SIWEI;
import com.sansec.kms.service.impl.Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import sun.net.www.content.text.plain;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class testSecKMS {

    static KMS_SIWEI kms_siwei = null;
    static String caHost = "10.0.64.63";
    static int caPort = 8086;
    static String raPassword = "66666666";
    static String raPkcs12 = "";


    public static void main(String[] args) throws InterruptedException {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("++++++++ SecKMS KMIP Client test  ++++++++++");
        System.out.println("              初始化参数配置               	");
        init();
        while (true) {

            int choice = -1;
            System.out.println("                                                                   ");
            System.out.println("  1 功能测试               	");
            System.out.println("  2 性能测试              	");
            System.out.println("                                                                   ");
            System.out.println(" 0 return                                                          ");
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            choice = InUtil.getSelect();
            if (choice == 0) {
                return;
            }
            if ((choice < 1) || (choice > 2)) {
                continue;
            }

            switch (choice) {
                case 1:
                    while (true) {
                        int choice1 = -1;
                        System.out.println("                                                                   		");
                        System.out.println("  1 genCIToolpfxAndKey  --fota创建证书，并下载pfx文件               	");
                        System.out.println("  2 getCertificate      --获取证书              					  	");
                        System.out.println("  3 getCertKey          --找回证书保护口令                           	");
                        System.out.println("  4 getSymmKey          --获取对称密钥  （获取K1密钥/获取fota  k2密钥） ");
                        System.out.println("  5 kmsSign             --签名   （对原始升级包的sha256进行签名）       ");
                        System.out.println("  6 kmsVerifyAPI        --验签   （API接口验签）                        ");
                        System.out.println("  7 kmsVerifyFile       --验签   （升级包验签）                         ");
                        System.out.println("  8 encrypt             --加密升级包                                    ");
                        System.out.println("  9 decrypt             --解密升级包                                    ");
                        System.out.println(" 10 genWhiteKeyPair     --创建白盒密钥对和接口验签证书                  ");
                        System.out.println(" 11 getWhiteCert        --获取接口验签证书                              ");
                        System.out.println(" 12 getWhiteKeyPair     --获取白盒密钥对                                ");
                        System.out.println("                                                                   ");
                        System.out.println(" 0 return                                                          ");
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        choice1 = InUtil.getSelect();
                        if (choice1 == 0) {
                            break;
                        }
                        if ((choice1 < 1) || (choice1 > 12)) {
                            continue;
                        }

                        switch (choice1) {
                            case 1:
                                System.out.println("testgenCIToolpfxAndKey");
                                testgenCIToolpfxAndKey();
                                break;
                            case 2:
                                System.out.println("testgetCert");
                                testgetCert();
                                break;
                            case 3:
                                System.out.println("testgetCertKey");
                                testgetCertKey();
                                break;
                            case 4:
                                System.out.println("testgetSymmKey");
                                testgetSymmKey();
                                break;
                            case 5:
                                System.out.println("testkmsSign");
                                testkmsSign();
                                break;
                            case 6:
                                System.out.println("testkmsVerifyAPI");
                                testkmsVerifyAPI();
                                break;
                            case 7:
                                System.out.println("testkmsVerifyFile");
                                testkmsVerifyFile();
                                break;
                            case 8:
                                System.out.println("testkmsEncrypt");
                                testkmsEncrypt();
                                break;
                            case 9:
                                System.out.println("testkmsDecrypt");
                                testkmsDecrypt();
                                break;
                            case 10:
                                System.out.println("genWhiteKeyPair");
                                testgenWhiteKeyPair();
                                break;
                            case 11:
                                System.out.println("getWhiteCert");
                                testgetWhiteCert();
                                break;
                            case 12:
                                System.out.println("getWhiteKeyPair");
                                getWhiteKeyPair();
                                break;
                            default:
                                break;

                        }
                    }
                    break;
                case 2:
                    while (true) {
                        int choice2 = -1;
                        System.out.println("                                                                   	  ");
                        System.out.println("  1 getSymmKey          --获取对称密钥  （获取K1密钥/获取fota  k2密钥） ");
                        System.out.println("  2 kmsSign             --签名   （对原始升级包的sha256进行签名）       ");
                        System.out.println("  3 kmsVerifyAPI        --验签   （API接口验签）                       ");
                        System.out.println("  4 kmsVerifyFile       --验签   （升级包验签）                        ");
                        System.out.println("  5 encrypt             --加密升级包                                   ");
                        System.out.println("  6 decrypt             --解密升级包                                   ");
                        System.out.println("  7 getCertificate      --获取证书              					  	   ");
                        System.out.println("  8 getCertKey          --找回证书保护口令                              ");
                        System.out.println("  9 getWhiteCert        --获取接口验签证书                              ");
                        System.out.println(" 10 getWhiteKeyPair     --获取白盒密钥对                                ");
                        System.out.println("                                                                   ");
                        System.out.println(" 0 return                                                          ");
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        choice2 = InUtil.getSelect();
                        if (choice2 == 0) {
                            break;
                        }
                        if ((choice2 < 1) || (choice2 > 10)) {
                            continue;
                        }
                        int count;
                        int threadNum;
                        long start;
                        String countString;
                        String threadNumString;
                        String para_kmsuser;
                        String para_kmspassword;
                        String para_cert_path;
                        String para_algName;
                        String para_plainPath;
                        String para_signPath;
                        String para_name;
                        String para_encryptPath;
                        String para_decryptPath;
                        switch (choice2) {
                            case 1:
                                System.out.println("testgetSymmKey");
                                para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
                                para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
                                para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
                                para_cert_path = InUtil.getString("请输入证书文件的地址", "./test.cer");
                                threadNumString = InUtil.getString("请输入线程的数目", "100");
//                                countString = InUtil.getString("请输入每个线程执行的次数", "100");
                                threadNum = Integer.parseInt(threadNumString);
                                count = Integer.parseInt("0");

                                TestGetSymmKey.test(kms_siwei, para_kmsuser, para_kmspassword, para_name, para_cert_path, threadNum, count);

//                                new Thread(new TPSPrint(TestGetSymmKey.GetSymmKey.class, "GetSymmKey")).start();
//                                //启动停止线程
//                                new Thread(new StopTest(TestGetSymmKey.GetSymmKey.class, TPSPrint.class)).start();
//                                for (int i = 0; i < threadNum; i++) {
//
//                                    new Thread(new TestGetSymmKey.GetSymmKey(kms_siwei, para_kmsuser, para_kmspassword, para_name, para_cert_path, count)).start();
//                                }


//                                for (int i = 0; i < count; i++) {
//                                    testgetSymmKey1(para_kmsuser,para_kmspassword,para_name,para_cert_path);
//                                }
//                                System.out.println("耗时："+(System.currentTimeMillis()-start)+"ms");
//
//                                Runnable runnable = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        testgetSymmKey1(para_kmsuser,para_kmspassword,para_name,para_cert_path);
//                                    }
//                                };
//                                ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
//                                for(int i=0;i<;i++){
//                                    executorService.submit(runnable);
//                                }
//                                synchronized(executorService) {
//                                executorService.wait();
//                                }
//                                executorService.wait();


//                                ExecutorService executorService = Executors.newFixedThreadPool(1);
//                                CountDownLatch latch= new CountDownLatch(1);
//                                for (int i = 0; i < 1; i++) {
//                                    executorService.submit(() -> {
//                                        TestGetSymmKey.test(kms_siwei, para_kmsuser, para_kmspassword, para_name, para_cert_path, threadNum, count);
//                                        latch.countDown();
//                                    });
//                                }
//                                latch.await();

//                                //启动打印线程
//                                new Thread(new TPSPrint(TestGetSymmKey.GetSymmKey.class, "GetSymmKey")).start();
//                                //启动停止线程
//                                new Thread(new StopTest(TestGetSymmKey.GetSymmKey.class, TPSPrint.class)).start();
//                                ExecutorService executorService = Executors.newFixedThreadPool(1);
//                                for (int i = 0; i < threadNum; i++) {
//                                    executorService.submit(() -> {
//                                        new TestGetSymmKey.GetSymmKey(kms_siwei, para_kmsuser, para_kmspassword, para_name, para_cert_path, count);
//                                    });
//                                }
//                                executorService.shutdown();
//                                try {
//                                    executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
                                break;
                            case 2:
                                System.out.println("testkmsSign");
                                para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
                                para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
                                String para_keyName = InUtil.getString("请输入密钥名称", "Fota-2019");
                                para_algName = InUtil.getString("请输签名算法名称", "SHA256WithRSA");
                                para_plainPath = InUtil.getString("请明文数据文件的路径", "./testPlainData.txt");
                                para_signPath = InUtil.getString("请输入签名数据文件的路径", "./testSignData.txt");
                                threadNumString = InUtil.getString("请输入线程的数目", "100");
//                                countString = InUtil.getString("请输入每个线程执行的次数", "100");
                                threadNum = Integer.parseInt(threadNumString);
                                count = Integer.parseInt("0");
                                TestKmsSign.test(kms_siwei, para_kmsuser, para_kmspassword, para_keyName, para_algName, para_plainPath, para_signPath, threadNum, count);

//                                ExecutorService executorService1 = Executors.newFixedThreadPool(1);
//                                CountDownLatch latch1= new CountDownLatch(1);
//                                for (int i = 0; i < 1; i++) {
//                                    executorService1.submit(() -> {
//                                        latch1.countDown();
//                                    });
//                                }
//                                latch1.await();
                                break;
                            case 3:
                                System.out.println("testkmsVerifyAPI");
                                para_cert_path = InUtil.getString("请输入证书文件的地址", "./test.cer");
                                para_algName = InUtil.getString("请输签名算法名称", "SHA256WithRSA");
                                para_plainPath = InUtil.getString("请明文数据文件的路径", "./testPlainData.txt");
                                para_signPath = InUtil.getString("请输入签名数据文件的路径", "./testSignData.txt");
                                threadNumString = InUtil.getString("请输入线程的数目", "100");
//                                countString = InUtil.getString("请输入每个线程执行的次数", "100");
                                threadNum = Integer.parseInt(threadNumString);
                                count = Integer.parseInt("0");
                                TestKmsVerifyAPI.test(kms_siwei, para_cert_path, para_algName, para_plainPath, para_signPath, threadNum, count);
                                break;
                            case 4:
                                System.out.println("testkmsVerifyFile");
                                para_cert_path = InUtil.getString("请输入证书文件的地址", "./test.cer");
                                para_algName = InUtil.getString("请输签名算法名称", "SHA256WithRSA");
                                para_plainPath = InUtil.getString("请明文数据文件的路径", "./testPlainData.txt");
                                para_signPath = InUtil.getString("请输入签名数据文件的路径", "./testSignData.txt");
                                threadNumString = InUtil.getString("请输入线程的数目", "100");
//                                countString = InUtil.getString("请输入每个线程执行的次数", "100");
                                threadNum = Integer.parseInt(threadNumString);
                                count = Integer.parseInt("0");
                                TestKmsVerifyFile.test(kms_siwei, para_cert_path, para_algName, para_plainPath, para_signPath, threadNum, count);
                                break;
                            case 5:
                                System.out.println("testkmsEncrypt");
                                para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
                                para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
                                para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
                                para_plainPath = InUtil.getString("请输入明文数据文件的路径", "./plain.zip");
                                para_encryptPath = InUtil.getString("请输入加密文件的路径", "./encrypt.text");
                                threadNumString = InUtil.getString("请输入线程的数目", "100");
//                                countString = InUtil.getString("请输入每个线程执行的次数", "100");
                                threadNum = Integer.parseInt(threadNumString);
                                count = Integer.parseInt("0");
                                TestKmsEncrypt.test(kms_siwei, para_kmsuser, para_kmspassword, para_name, para_plainPath, para_encryptPath, threadNum, count);
                                break;
                            case 6:
                                System.out.println("testkmsDecrypt");
                                para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
                                para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
                                para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
                                para_encryptPath = InUtil.getString("请输入加密文件的路径", "./encrypt.text");
                                para_decryptPath = InUtil.getString("请输入解密后文件的路径", "./decrypt.zip");
                                threadNumString = InUtil.getString("请输入线程的数目", "100");
//                                countString = InUtil.getString("请输入每个线程执行的次数", "100");
                                threadNum = Integer.parseInt(threadNumString);
                                count = Integer.parseInt("0");
                                TestKmsDecrypt.test(kms_siwei, para_kmsuser, para_kmspassword, para_name, para_encryptPath, para_decryptPath, threadNum, count);
                                break;
                            case 7:
                                System.out.println("testgetCert");
                                para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
                                para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
                                para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
                                threadNumString = InUtil.getString("请输入线程的数目", "100");
//                                countString = InUtil.getString("请输入每个线程执行的次数", "100");
                                threadNum = Integer.parseInt(threadNumString);
                                count = Integer.parseInt("0");
                                TestGetCert.test(kms_siwei, para_kmsuser, para_kmspassword, para_name, threadNum, count);
                                break;
                            case 8:
                                System.out.println("testgetCertKey");
                                para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
                                para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
                                para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
                                threadNumString = InUtil.getString("请输入线程的数目", "100");
//                                countString = InUtil.getString("请输入每个线程执行的次数", "100");
                                threadNum = Integer.parseInt(threadNumString);
                                count = Integer.parseInt("0");
                                TestGetCertKey.test(kms_siwei, para_kmsuser, para_kmspassword, para_name, threadNum, count);
                                break;
                            case 9:
                                System.out.println("getWhiteCert");
                                para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
                                para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
                                para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
                                threadNumString = InUtil.getString("请输入线程的数目", "100");
//                                countString = InUtil.getString("请输入每个线程执行的次数", "100");
                                threadNum = Integer.parseInt(threadNumString);
                                count = Integer.parseInt("0");
                                TestGetWhiteCert.test(kms_siwei, para_kmsuser, para_kmspassword, para_name, threadNum, count);
                                break;
                            case 10:
                                System.out.println("getWhiteKeyPair");
                                para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
                                para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
                                para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
                                threadNumString = InUtil.getString("请输入线程的数目", "100");
//                                countString = InUtil.getString("请输入每个线程执行的次数", "100");
                                threadNum = Integer.parseInt(threadNumString);
                                count = Integer.parseInt("0");
                                TestGetWhiteKeyPair.test(kms_siwei, para_kmsuser, para_kmspassword, para_name, threadNum, count);
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public static void init() {
//		String para_kmsuser = InUtil.getString("请输入kmipConfig.ini文件的路径", "E:\\三未信安\\四维图新\\现场技术支持\\kmipConfig.ini");
//		String para_caHost = InUtil.getString("请输入caHost", "192.168.145.71");
//		String para_raPkcs12 = InUtil.getString("请输入pfx文件内容", "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");
        String para_kmsuser = InUtil.getString("请输入kmipConfig.ini文件的路径", "kmipConfig.ini");
        if (StringUtils.isNotBlank(para_kmsuser) && ("kmipConfig.ini".equals(para_kmsuser))) {
            para_kmsuser = TestGetSymmKey.class.getResource("kmipConfig.ini").getPath();
        }
        String para_caHost = InUtil.getString("请输入caHost", "10.0.64.63");
        String para_raPkcs12 = InUtil.getString("请输入pfx文件内容", "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");
        String para_caPort = InUtil.getString("请输入caPort", "8086");
        String para_raPassword = InUtil.getString("请输入raPassword", "66666666");

        kms_siwei = new KMS_SIWEI(para_kmsuser);
        caHost = para_caHost;
        caPort = Integer.parseInt(para_caPort);
        raPassword = para_raPassword;
        raPkcs12 = para_raPkcs12;
    }

    public static void testgenCIToolpfxAndKey() {
        String para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
        String para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
        String para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
        String para_passwd = InUtil.getString("请输入证书保护口令", "0531-1passwd");
        int para_validMonth = 0;
        while (para_validMonth == 0) {
            String tmpStr = InUtil.getString("请输入证书有效期，月份数量", "24");
            para_validMonth = Integer.valueOf(tmpStr);
        }
        String para_userInfo = InUtil.getString("请输入用户证书的DName项，例如：CN=xxx车厂名称,O=xx,OU=xx,C=CN", "CN=0603-1,C=CN");
        String para_signAlg = InUtil.getString("请输入签名算法标识字符串", "SHA256WithRSA");
        int para_asymKeySize = 0;
        while ((para_asymKeySize != 1024) && (para_asymKeySize != 2048)) {
            String tmpStr = InUtil.getString("请输入RSA非对称密钥长度（1024，2048）", "2048");
            para_asymKeySize = Integer.valueOf(tmpStr);
        }
        int para_symmKeySize = 0;
        while ((para_symmKeySize != 128) && (para_symmKeySize != 256)) {
            String tmpStr = InUtil.getString("请输入AES对称密钥的长度（目前只支持128）", "128");
            para_symmKeySize = Integer.valueOf(tmpStr);
        }
        String para_symmAlg = InUtil.getString("请输入对称密钥算法标识，AES", "AES");

        Result result = kms_siwei.genCIToolpfxAndKey(para_kmsuser, para_kmspassword, para_name, para_passwd, para_validMonth, para_userInfo, para_signAlg, para_asymKeySize, para_symmKeySize, para_symmAlg, caHost, caPort, raPkcs12, raPassword);
        System.out.println(JSON.toJSONString(result));
    }

    public static void testgetCert() {
        String para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
        String para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
        String para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");

        Result cert = kms_siwei.getCertificate(para_kmsuser, para_kmspassword, para_name);
        System.out.println(JSON.toJSONString(cert));
    }

    public static void testgetCert1(String para_kmsuser, String para_kmspassword, String para_name) {

        Result cert = kms_siwei.getCertificate(para_kmsuser, para_kmspassword, para_name);
        System.out.println(JSON.toJSONString(cert));
    }

    public static void testgetCertKey() {
        String para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
        String para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
        String para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
        Result certKey = kms_siwei.getCertKey(para_kmsuser, para_kmspassword, para_name);
        System.out.println(JSON.toJSONString(certKey));
    }

    public static void testgetCertKey1(String para_kmsuser, String para_kmspassword, String para_name) {
        Result certKey = kms_siwei.getCertKey(para_kmsuser, para_kmspassword, para_name);
        System.out.println(JSON.toJSONString(certKey));
    }

    public static void testgetSymmKey() {
        String para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
        String para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
        String para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
        String para_cert_path = InUtil.getString("请输入证书文件的地址", "./test.cer");

        byte[] bytes = null;
        Result symmKey = null;
        if (StringUtils.isNotBlank(para_cert_path)) {
            try {
                bytes = Util.readFromFile(para_cert_path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new String(bytes));
            symmKey = kms_siwei.getSymmKey(para_kmsuser, para_kmspassword, para_name, new String(bytes));
        } else {
            symmKey = kms_siwei.getSymmKey(para_kmsuser, para_kmspassword, para_name, null);
        }
        System.out.println(JSON.toJSONString(symmKey));
    }

    public static void testgetSymmKey1(String para_kmsuser, String para_kmspassword, String para_name, String para_cert_path) {
        byte[] bytes = null;
        Result symmKey = null;
//        synchronized ("hello"){
        if (StringUtils.isNotBlank(para_cert_path)) {
            try {
                bytes = Util.readFromFile(para_cert_path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new String(bytes));
            symmKey = kms_siwei.getSymmKey(para_kmsuser, para_kmspassword, para_name, new String(bytes));
        } else {
            symmKey = kms_siwei.getSymmKey(para_kmsuser, para_kmspassword, para_name, null);
        }
        System.out.println(JSON.toJSONString(symmKey));
//        }

    }

    public static void testkmsSign() {
        String para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
        String para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
        String para_keyName = InUtil.getString("请输入密钥名称", "Fota-2019");
        String para_algName = InUtil.getString("请输签名算法名称", "SHA256WithRSA");
        String para_plainPath = InUtil.getString("请明文数据文件的路径", "./testPlainData.txt");
        String para_signPath = InUtil.getString("请输入签名数据文件的路径", "./testSignData.txt");

        Result result = kms_siwei.kmsSign(para_kmsuser, para_kmspassword, para_keyName, para_algName, para_plainPath);
        System.out.println(JSON.toJSONString(result));
        try {
            Util.writeToFile(para_signPath, (byte[]) result.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testkmsSign1(String para_kmsuser, String para_kmspassword, String para_keyName, String para_algName, String para_plainPath, String para_signPath) {
        Result result = kms_siwei.kmsSign(para_kmsuser, para_kmspassword, para_keyName, para_algName, para_plainPath);
//        System.out.println(JSON.toJSONString(result));
        try {
            Util.writeToFile(para_signPath, (byte[]) result.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testkmsVerifyAPI() {
        String para_cert_path = InUtil.getString("请输入证书文件的地址", "./test.cer");
        String para_algName = InUtil.getString("请输签名算法名称", "SHA256WithRSA");
        String para_plainPath = InUtil.getString("请明文数据文件的路径", "./testPlainData.txt");
        String para_signPath = InUtil.getString("请输入签名数据文件的路径", "./testSignData.txt");
        byte[] bytes = new byte[0];
        try {
            bytes = Util.readFromFile(para_signPath);
//			bytes = Base64Utils.decodeFromString(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] plainData = new byte[0];
        try {
            plainData = Util.readFromFile(para_plainPath);
        } catch (IOException e) {
            e.printStackTrace();
        }


        byte[] bytes2 = new byte[0];
        try {
            bytes2 = Util.readFromFile(para_cert_path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result result = kms_siwei.kmsVerifyByte(new String(bytes2), para_algName, plainData, bytes);
        System.out.println(JSON.toJSONString(result));
    }

    public static void testkmsVerifyAPI1(String para_cert_path, String para_algName, String para_plainData, String para_signPath) {
        byte[] bytes = new byte[0];
        try {
            bytes = Util.readFromFile(para_signPath);
//			bytes = Base64Utils.decodeFromString(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] plainData = new byte[0];
        try {
            plainData = Util.readFromFile(para_plainData);
        } catch (IOException e) {
            e.printStackTrace();
        }


        byte[] bytes2 = new byte[0];
        try {
            bytes2 = Util.readFromFile(para_cert_path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result result = kms_siwei.kmsVerifyByte(new String(bytes2), para_algName, plainData, bytes);
        System.out.println(JSON.toJSONString(result));
    }

    public static void testkmsVerifyFile() {
        String para_cert_path = InUtil.getString("请输入证书文件的地址", "./test.cer");

        String para_algName = InUtil.getString("请输签名算法名称", "SHA256WithRSA");
        String para_plainPath = InUtil.getString("请明文数据文件的路径", "./testPlainData.txt");
        String para_signPath = InUtil.getString("请输入签名数据文件的路径", "./testSignData.txt");

        byte[] bytes = new byte[0];
        try {
            bytes = Util.readFromFile(para_signPath);
//			bytes = Base64Utils.decodeFromString(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes2 = new byte[0];
        try {
            bytes2 = Util.readFromFile(para_cert_path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result result = kms_siwei.kmsVerifyFile(new String(bytes2), para_algName, para_plainPath, bytes);
        System.out.println(JSON.toJSONString(result));
    }

    public static void testkmsVerifyFile1(String para_cert_path, String para_algName, String para_plainData, String para_signPath) {
        byte[] bytes = new byte[0];
        try {
            bytes = Util.readFromFile(para_signPath);
//			bytes = Base64Utils.decodeFromString(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes2 = new byte[0];
        try {
            bytes2 = Util.readFromFile(para_cert_path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result result = kms_siwei.kmsVerifyFile(new String(bytes2), para_algName, para_plainData, bytes);
        System.out.println(JSON.toJSONString(result));
    }

    public static void testkmsEncrypt() {
        String para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
        String para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
        String para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
        String para_plainPath = InUtil.getString("请输入明文数据文件的路径", "./plain.zip");
        String para_encryptPath = InUtil.getString("请输入加密文件的路径", "./encrypt.text");

        Result weitest5 = kms_siwei.getSymmKey(para_kmsuser, para_kmspassword, para_name, null);

        Result encrypt = kms_siwei.encrypt(para_plainPath, para_encryptPath, (String) weitest5.getData());


        System.out.println((JSON.toJSONString(encrypt)));
    }

    public static void testkmsEncrypt1(String para_kmsuser, String para_kmspassword, String para_name, String para_plainPath, String para_encryptPath) {

        Result weitest5 = kms_siwei.getSymmKey(para_kmsuser, para_kmspassword, para_name, null);

        Result encrypt = kms_siwei.encrypt(para_plainPath, para_encryptPath, (String) weitest5.getData());


        System.out.println((JSON.toJSONString(encrypt)));
    }

    public static void testkmsDecrypt() {
        String para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
        String para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
        String para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
        String para_encryptPath = InUtil.getString("请输入加密文件的路径", "./encrypt.text");
        String para_decryptPath = InUtil.getString("请输入解密后文件的路径", "./decrypt.zip");
        Result weitest5 = kms_siwei.getSymmKey(para_kmsuser, para_kmspassword, para_name, null);

        Result encrypt = kms_siwei.decrypt(para_encryptPath, para_decryptPath, (String) weitest5.getData());
        System.out.println((JSON.toJSONString(encrypt)));
    }

    public static void testkmsDecrypt1(String para_kmsuser, String para_kmspassword, String para_name, String para_encryptPath, String para_decryptPath) {
        Result weitest5 = kms_siwei.getSymmKey(para_kmsuser, para_kmspassword, para_name, null);

        Result encrypt = kms_siwei.decrypt(para_encryptPath, para_decryptPath, (String) weitest5.getData());
        System.out.println((JSON.toJSONString(encrypt)));
    }

    public static void testgenWhiteKeyPair() {
        String para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
        String para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
        String para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
        int para_validMonth = 0;
        while (para_validMonth == 0) {
            String tmpStr = InUtil.getString("请输入证书有效期，月份数量", "24");
            para_validMonth = Integer.valueOf(tmpStr);
        }
        String para_userInfo = InUtil.getString("请输入用户证书的DName项，例如：CN=xxx车厂名称,O=xx,OU=xx,C=CN", "CN=0603-1,C=CN");
        String para_signAlg = InUtil.getString("请输入签名算法标识字符串", "SHA256WithRSA");

        Result weitest5 = kms_siwei.genWhiteKeyPair(para_kmsuser, para_kmspassword, para_name, para_validMonth, para_userInfo, para_signAlg, caHost, caPort, raPkcs12, raPassword);

        System.out.println(JSON.toJSONString(weitest5));
    }

    public static void testgetWhiteCert() {
        String para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
        String para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
        String para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
        Result result = kms_siwei.getWhiteCert(para_kmsuser, para_kmspassword, para_name);
        System.out.println(JSON.toJSONString(result));
    }

    public static void testgetWhiteCert1(String para_kmsuser, String para_kmspassword, String para_name) {
        Result result = kms_siwei.getWhiteCert(para_kmsuser, para_kmspassword, para_name);
        System.out.println(JSON.toJSONString(result));
    }

    public static void getWhiteKeyPair() {
        String para_kmsuser = InUtil.getString("请输入KMS用户名", "fota");
        String para_kmspassword = InUtil.getString("请输入KMS用户密码", "Siwei1234.");
        String para_name = InUtil.getString("请输入车厂唯一标识", "0603-1");
        Result result = kms_siwei.getWhiteKeyPair(para_kmsuser, para_kmspassword, para_name);
        System.out.println(JSON.toJSONString(result));
    }

    public static void getWhiteKeyPair1(String para_kmsuser, String para_kmspassword, String para_name) {
        Result result = kms_siwei.getWhiteKeyPair(para_kmsuser, para_kmspassword, para_name);
        System.out.println(JSON.toJSONString(result));
    }

}
