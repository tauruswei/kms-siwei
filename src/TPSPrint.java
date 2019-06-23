import com.sansec.kms.service.impl.Util;

import java.util.Date;

public class TPSPrint implements Runnable {
	@SuppressWarnings("rawtypes")
	private Class classOperation;
	@SuppressWarnings("rawtypes")
	public TPSPrint(Class classOperation) {
		running = true;
		this.classOperation = classOperation;
	}

	String filename = null;
	@SuppressWarnings("rawtypes")
	public TPSPrint(Class classOperation, String filename) {
		running = true;
		this.classOperation = classOperation;
		this.filename = filename;
		System.out.println(filename);
	}

	public static boolean running = true;
	public static void exit() {
		running = false;
		System.out.println("exit");
	}

	public void run() {
		int okCount = 0;
		int failCount = 0;
		int exCount = 0;
		int seconds = 0;
		String msg = "";
		while (running) {
			if( seconds % 32 == 0) {
				System.out.println("Press q stop test ... "); //按q停止 ...
				System.out.println("\tTime\t\t\tCorrect speed(TPS)\tAverage error(TPS)\tAverage abnormal(TPS)\tAverage correct speed(TPS)\t"+filename/*.substring(0, filename.lastIndexOf(".txt")).replace('-', '/')*/);
			}

			okCount = Util.get(classOperation, "okCount");
			failCount = Util.get(classOperation, "failCount");
			exCount = Util.get(classOperation, "exCount");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			msg = Util.sdf.format(new Date()) + "\t\t\t"
			+ (Util.get(classOperation, "okCount") - okCount) + "\t\t\t"
			+ (Util.get(classOperation, "failCount")-failCount)+"\t\t\t"
			+ (Util.get(classOperation, "exCount")-exCount)+"\t\t\t\t"
			+ Util.get(classOperation, "okCount")/(++seconds);
			System.out.println(msg);

		}
		running = true;
	}
}
