import java.lang.reflect.Field;
import java.util.Scanner;

public class StopTest implements Runnable{
	@SuppressWarnings("rawtypes")
	private Class claszOperation = null;
	@SuppressWarnings("rawtypes")
	private Class claszPrint = null;
	private boolean running = true;
	public StopTest(Class claszOperation, Class claszPrint) {
		this.claszOperation = claszOperation;
		this.claszPrint = claszPrint;
	}
	
	public void run() {

		while(running) {
			@SuppressWarnings("resource")
			String str = new Scanner(System.in).next();

			if("q".equalsIgnoreCase(str)) {
				int i=0;
				while(setBoolean(claszOperation, "running", false)){
					break;
				};

				i=0;
				while(setBoolean(claszPrint, "running", false)){
					break;
				};
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				running = false;
			}
			
		}
	}
	public static void main(String[] args) {
	}
	@SuppressWarnings("rawtypes")
	public static boolean setBoolean(Class clasz, String fieldName, boolean flag) {
		// 获得对象的类型
		try {
			
			Field f = clasz.getDeclaredField("running"); 
			f.setBoolean(clasz, false);

			return f.getBoolean(clasz);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
