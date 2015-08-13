package com.common.tools.format;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import android.util.Log;

public class ByteHelper {
	
	/**
	 * 字节数组相减
	 * @param source
	 * @param start
	 * @param len
	 * @return
	 */
	public static byte[] byteArraySub(byte[] source, int start, int len) {
		byte[] des = new byte[len];
		for(int i=0; i<len; i++) {
			des[i] = source[start+i];
		}
		return des;
	}
	
	/**
	 * 字节数组相加
	 * @param bytes 多个数组
	 * @return
	 */
	public static byte[] byteArrayAdd(byte[]... bytes) {
		byte[] result = bytes[0];
		for(int i=1; i<bytes.length; i++) {
			result = byteArrayAdd(result, bytes[i]);
		}		
		return result;
	}
	
	/**
	 * 字节数组相加
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte[] byteArrayAdd(byte[] a, byte[] b) {
		byte c[] = new byte[a.length + b.length];
		int i;
		for(i=0; i<a.length; i++) {
			c[i] = a[i];
		}
		for(int j=0; j<b.length; j++) {			
			c[i] = b[j];	
			i++;
		}
		return c;
	}
	
	/**
	 * 字节替换
	 * @param a
	 * @param b
	 * @param len
	 * @return
	 */
	public static byte[] byteArrayReplace(byte[] a, byte[] b, int start, int len) {
		for(int i=start, j=0; j<len; i++, j++) {
			a[i] = b[j];
		}
		
		return a;
	}
	
	public static byte[] getBytes (char[] chars) {
		Charset cs = Charset.forName ("UTF-8");
		CharBuffer cb = CharBuffer.allocate (chars.length);
		cb.put (chars);
		cb.flip ();
		ByteBuffer bb = cs.encode (cb);
		  
		return bb.array();
	}
	
	/**
	 * 
	 * @描述: 将字符串转换成指定长度的字节
	 *        1. 长度不足，则补0x20
	 *        2. 长度超过，则截断
	 *        
	 * @param src
	 * @param length
	 * @return 
	 */
	public static byte[] getParamsByte(String src, int length){
		byte[] params = null;		
		try {
			byte[] srcs = src.getBytes("UTF-8");		
			if(srcs.length < length) {
				params = new byte[length];
				ByteHelper.byteArrayReplace(params, srcs, 0, srcs.length);
				for(int i=srcs.length; i<length; i++) {
					params[i] = 0x20;
				}
			} else if(srcs.length == length) {				
				params = srcs;
			} else {
				params = ByteHelper.byteArraySub(srcs, 0, length);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return params;
	}
	
	public static byte[] getHexParamsByte(String hexSrc, int length) {
		byte[] params = new byte[length];
		byte[] srcs;
		srcs = HexStringBytes.String2Bytes(hexSrc);		
		if(srcs.length < length) {
			ByteHelper.byteArrayReplace(params, srcs, 0, srcs.length);
			for(int i=srcs.length; i<length; i++) {
				params[i] = 0x20;
			}
		} else {
			params = srcs;
		}
		return params;
	}
}
