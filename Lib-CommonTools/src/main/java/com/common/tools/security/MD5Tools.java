package com.common.tools.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.common.tools.format.HexStringBytes;

/**
 * 
 * @描述：MD5工具类
 * @作者：liang bao xian
 * @时间：2014年8月1日 下午2:34:15
 */
public class MD5Tools {
	
	/**
	 * @描述: 字符串转MD5, 输出小写
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getDigest(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("md5");
		md.update(input.getBytes());
		byte[] hash = md.digest();
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		int j = hash.length;
		char buf[] = new char[j * 2];
		int k = 0;
		for (int i = 0; i < j; i++) {
			byte b = hash[i];
			buf[k++] = hexDigits[b >>> 4 & 0xf];
			buf[k++] = hexDigits[b & 0xf];
		}
		return new String(buf);
	}	
	
	
	public static String getDigestFromFile(File file) {
		InputStream fis;  
        byte[] buffer = new byte[1024];  
        int numRead = 0;  
        MessageDigest md5;  
        try{  
            fis = new FileInputStream(file.getAbsoluteFile());  
            md5 = MessageDigest.getInstance("MD5");  
            while((numRead=fis.read(buffer)) > 0) {  
                md5.update(buffer,0,numRead);  
            }  
            fis.close();  
            return HexStringBytes.bytes2HexString(md5.digest());     
        } catch (Exception e) {  
            System.out.println("error");  
            return null;  
        }  
	}
	
	/**
	 * 
	 * @描述: 字符串转MD5, 输出大写
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String getDigestUpperCase(String input) throws NoSuchAlgorithmException {
		return getDigest(input).toUpperCase();
	}
}
