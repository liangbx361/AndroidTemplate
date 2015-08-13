package com.common.tools.file;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileEncrypt {
	
	private static final int REVERSE_LENGTH = 56;
	
	/**
	 * 
	 * @描述: 解密
	 * @param filePath
	 * @return
	 */
	public static boolean decrypt(String filePath) {
		return encrypt(filePath);
	}
	
	/**
	 * 
	 * @描述: 加密
	 * @param filePath
	 * @return
	 */
	public static boolean encrypt(String filePath) {
		int len = REVERSE_LENGTH;
		try {
			File f = new File(filePath);
			RandomAccessFile raf = new RandomAccessFile(f, "rw");
			long totalLen = raf.length();

			if (totalLen < REVERSE_LENGTH)
				len = (int) totalLen;

			FileChannel channel = raf.getChannel();
			MappedByteBuffer buffer = channel.map(
					FileChannel.MapMode.READ_WRITE, 0, REVERSE_LENGTH);
			byte tmp;
			for (int i = 0; i < len; ++i) {
				byte rawByte = buffer.get(i);
				tmp = (byte) (rawByte ^ i);
				buffer.put(i, tmp);
			}
			buffer.force();
			buffer.clear();
			channel.close();
			raf.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
