package com.common.tools.format;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import android.renderscript.Type.CubemapFace;

public class ByteChar {

	public static byte[] getBytes(char[] chars) {
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = CharBuffer.allocate(chars.length);
		cb.put(chars);
		cb.flip();
		ByteBuffer bb = cs.encode(cb);

		return bb.array();
	}

	public static char[] getChars(byte[] bytes) {
		Charset cs = Charset.forName("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		bb.flip();
		CharBuffer cb = cs.decode(bb);

		return cb.array();
	}
	
	public static String subByte2String(byte[] byteArray, int offest, int len) {		
		byte[] subByte = ByteHelper.byteArraySub(byteArray, offest, len);
		char[] subChar = getChars(subByte);
		if(subChar.length != len) {
			char[] tempChar = new char[len];
			for(int i=0; i<len; i++) {
				tempChar[i] = subChar[i];
			}
			subChar = tempChar;
		}
		return String.valueOf(subChar);
	}
}
