import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class InUtil {
	public static final int defaultlen = 256;
	public static int getInput(String promptString) {
		System.out.print(promptString);
		int out = -1;
		try {
			String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
			if("".equals(str)) {
				out = -1;
			} else {
				out = Integer.parseInt(str);
			}
		} catch (Exception e) {
			out = -2;
		}
		
		return out;
    }
	
	public static int getSelect() {
		String promptString = "select[0]: ";
		int n = getInput(promptString);
		if(n == -1)	{
			n = 0;
		}
		
		return n;
	}
	
	public static int createSM2KeySize() {
		int keylength = -1;
		while ((keylength != 256)) {
			keylength = getInput("Enter the key module length [256]: ");
			if(keylength == -1) {
				keylength = 256;
			}
		}
		
		return keylength;
	}
	
	public static int createRSAKeySize() {
		int keylength = -1;
		while ((keylength != 1024) && (keylength != 2048) && (keylength != 3072) && (keylength != 4096)) {
			keylength = getInput("Enter the key module length(1024, 2048, 3072, 4096) [1024]: ");
			if(keylength == -1) {
				keylength = 1024;
			}
		}
		
		return keylength;
	}
	
	public static int createRSAKeyIndex() {
		return createKeyIndex(1, 32768);
	}
	
	public static int createSM2KeyIndex() {
		return createKeyIndex(1, 32768);
	}
	
	public static int createSM1KeyIndex() {
		return createKeyIndex(1, 32768);
	}
	public static int createSymmetryKeyIndex() {
		return createKeyIndex(1, 32768);
	}
	
	public static int createSM1KeySize() {
		int keylength = -1;
		while ((keylength != 128)) {
			keylength = getInput("Please Input the Key Length 128(default): ");
			if(keylength == -1) {
				keylength = 128;
			}
		}
		
		return keylength;
	}
	
	public static int createSymmetryKeySize() {
		int keylength = -1;
		while ((keylength%64 != 0)) {
			keylength = getInput("Enter the key length[128]: ");
			if(keylength == -1) {
				keylength = 128;
			}
		}
		
		return keylength;
	}
	
	public static int create3DESKeySize() {
		int keylength = -1;
		while ((keylength%64 != 0)) {
			keylength = getInput("Enter the key length [192]: ");
			if(keylength == -1) {
				keylength = 192;
			}
		}
		
		return keylength;
	}
	
	private static int createKeyIndex(int min, int max) {
		int keynum = -1;
		while ((keynum < min) || (keynum > max)) {
			keynum = getInput("Enter the key number("+min+"-"+max+") ["+min+"]: ");
			if(keynum == -1) {
				keynum = min;
			}
		}
		return keynum;
	}
	
	public static int createThreadNumber() {
		int threadnum = -1;
		while ((threadnum < 1) || (threadnum > 200)) {
			threadnum = getInput("Enter the number of threads(1-200) [50]: ");
			if(threadnum == -1) {
				threadnum = 50;
			}
		}
		
		return threadnum;
	}
	
	public static int createCycleCount() {
		int cycleCount = -1;
		while ((cycleCount < 1) || (cycleCount > 30000000)) {
			cycleCount = getInput("Enter the number of cycles(1-30000000) [30000000]: ");
			if(cycleCount == -1) {
				cycleCount = 30000000;
			}
		}
		
		return cycleCount;
	}	
	public static byte[] getBytes(String defaultPlain) {
		byte[] data = null;
        while( true ) {
        	System.out.print("Input data["+defaultPlain+"]: ");
        	try {
				String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
				if("".equals(str)) {
					data = defaultPlain.getBytes();
				} else {
					data = str.getBytes();
				}
				break;
			} catch (Exception e) {
				data = defaultPlain.getBytes();
			}
        }
        
        
        return data;
    }
	
	
	public static String getString(String  prompt, String defaultPlain) {
		String data = null;
        while( true ) {
        	System.out.print(prompt+"Default data["+defaultPlain+"]: ");
        	try {
				String str = new BufferedReader(new InputStreamReader(System.in)).readLine();
				if("".equals(str)) {
					data = defaultPlain;
				} else {
					data = str;
				}
				break;
			} catch (Exception e) {
				data = defaultPlain;
			}
        }
        
        
        return data;
    }	

	
	public static void main(String[] args) {
		/*byte[] tmp = getBytes(32);
		System.out.println(new String(tmp));*/
	}

	public static int creatStatistics(int defaultStat) {
		int statistics = -1;
		while (statistics<0) {
			statistics = getInput("Please Input Statistics "+defaultStat+"(default): ");
			if(statistics == -1) {
				statistics = defaultStat;
			}
		}
		
		return statistics;
	}

	public static int getDataLength(int defaultLen, int minLen, int maxLen) {
		int dataLength = -1;
		while (dataLength<minLen || dataLength>maxLen) {
			dataLength = getInput("Enter the data length("+minLen+"-"+maxLen+") ["+defaultLen+"]: ");
			if(dataLength == -1) {
				dataLength = defaultLen;
			}
		}
		
		return dataLength;
	}
	
	public static String getDataFilePath() {
		String path;
		System.out.println("Please Input Data File Path");
		path = new Scanner(System.in).next();
		while (!new File(path).isFile()) {
			System.out.println("Please Input Valid Data File Path");
			path = new Scanner(System.in).next();
		}
		return path;
	}
	
	public static byte[] getData(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[fis.available()];
			/*int n = fos.available();
			while(n>0) {
				
			}*/
			int pos = 0;
			int len = fis.available();
			int readLen = fis.read(buffer, pos, len);
			len -= readLen;
			pos += readLen;
			while(len > 0) {
				readLen = fis.read(buffer, pos, len);
				len -= readLen;
				pos += readLen;
			}
			
			return buffer;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
