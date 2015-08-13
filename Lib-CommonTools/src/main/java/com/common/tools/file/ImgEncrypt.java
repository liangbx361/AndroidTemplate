package com.common.tools.file;
import java.io.File;
import java.io.RandomAccessFile;


public class ImgEncrypt {
	
	public static final String KEY = "encrypt";
	
	/**
	 * 
	 * @����: �ж�ͼƬ�Ƿ����
	 * @param filePath
	 * @return
	 */
	public static boolean isEncrypt(String filePath) {
		try {
			byte[] keyBytest = KEY.getBytes();
			File f = new File(filePath);
			RandomAccessFile raf = new RandomAccessFile(f, "rw");
			
			byte[] headBytes = new byte[keyBytest.length];
			raf.seek(0);
			raf.read(headBytes);
			raf.close();
			
			String headKey = new String(headBytes);
			if(headKey.equals(KEY)) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @����: ͼƬ����
	 * @param filePath
	 * @return
	 */
	public static boolean decrypt(String filePath) {
		try {			
			if(isEncrypt(filePath)) {
				byte[] keyBytest = KEY.getBytes();
				File f = new File(filePath);
				RandomAccessFile raf = new RandomAccessFile(f, "rw");
			
				byte[] headBytes = new byte[keyBytest.length];
				raf.seek(f.length() - keyBytest.length);
				raf.read(headBytes);
				raf.seek(0);
				raf.write(headBytes);
			
				raf.setLength(f.length() - keyBytest.length);
			
				raf.close();
			}
			return true;			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 
	 * @����: ͼƬ����
	 * @param filePath
	 * @return
	 */
	public static boolean encrypt(String filePath) {
		try {	
			if(!isEncrypt(filePath)) {
				byte[] keyBytest = KEY.getBytes();
				File f = new File(filePath);
				RandomAccessFile raf = new RandomAccessFile(f, "rw");
			
				byte[] headBytes = new byte[keyBytest.length];
				raf.seek(0);
				raf.read(headBytes);
				raf.seek(0);
				raf.write(keyBytest);
			
				long fileLength = raf.length();			
				raf.setLength(fileLength + keyBytest.length);
				raf.seek(fileLength);						
				raf.write(headBytes);
			
				raf.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
