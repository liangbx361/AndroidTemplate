package com.common.tools.format;

/**
 * 锟斤拷锟斤拷十锟斤拷锟斤拷锟斤拷址锟斤拷锟街斤拷锟斤拷锟斤拷锟斤拷转锟斤拷
 * 锟斤拷锟斤拷说锟斤拷:
 * 1. HexString2Bytes锟斤拷十锟斤拷锟斤拷锟斤拷址锟阶拷锟斤拷锟斤拷纸锟斤拷锟斤拷椋拷锟教拷锟斤拷锟斤拷锟?
 * 2. bytes2HexString锟斤拷十锟斤拷锟斤拷锟斤拷纸锟斤拷锟斤拷锟阶拷锟斤拷锟斤拷址锟斤拷锟教拷锟斤拷锟斤拷锟?
 * @author liangbx
 *
 */
public class HexStringBytes {
	
	private static final char[] DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z'
    };
	
	private static final char[] UPPER_CASE_DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z'
    };
	
	/**
	 * 锟斤拷取十锟斤拷锟斤拷锟斤拷纸锟斤拷锟斤拷锟斤拷锟斤拷锟?锟斤拷锟节革拷式锟斤拷锟斤拷锟?
	 * @param command
	 * @return
	 */
	public static String getHexByteStr(byte[] command) {
		String str = "";
		for(int i=0; i<command.length; i++) {				
			str += byteToHexString((int)command[i], true, 0) + " ";
		}
		return str;
	}
	
	private static String byteToHexString(int i, boolean upperCase, int minWidth) {
        int bufLen = 2;  // Max number of hex digits in an int
        char[] buf = new char[bufLen];
        int cursor = bufLen;

        char[] digits = upperCase ? UPPER_CASE_DIGITS : DIGITS;
        do {
        	buf[--cursor] = digits[i & 0xf];
        	i >>= 4;
        } while (cursor > 0);

        return String.valueOf(buf);
    }
	
	/**
	 * 16锟斤拷锟斤拷锟街凤拷转锟街斤拷锟斤拷锟斤拷
	 * @param hexStr
	 * @return
	 */
	public static byte[] String2Bytes(String hexStr) {
		byte[] ret = new byte[hexStr.length()/2];
		byte[] tmp = hexStr.getBytes();
		for (int i = 0; i < hexStr.length()/2; ++i) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}
	
	/**
	 * 锟斤拷锟街斤拷锟斤拷锟斤拷转锟斤拷锟斤拷16锟斤拷锟斤拷锟街凤拷
	 * @param bArray
	 * @return
	 */
	public static final String bytes2HexString(byte[] bArray) { 
	    StringBuffer sb = new StringBuffer(bArray.length); 
	    String sTemp; 
	    for (int i = 0; i < bArray.length; i++) { 
	     sTemp = Integer.toHexString(0xFF & bArray[i]); 
	     if (sTemp.length() < 2) 
	      sb.append(0); 
	     sb.append(sTemp.toUpperCase()); 
	    } 
	    return sb.toString(); 
	}
		
	private static byte uniteBytes(byte src0, byte src1) {
	     byte _b0 = Byte.decode("0x" + new String(new byte[] {src0})).byteValue();
	     _b0 = (byte) (_b0 << 4);
	     byte _b1 = Byte.decode("0x" + new String(new byte[] {src1})).byteValue();
	     byte ret = (byte) (_b0 | _b1);
	     return ret;
	}
	
	/**
	 * 16杩涘埗瀛楃涓茶浆鏁村舰锛堟敞锛氫粎闄?涓崄鍏繘鍒跺瓧绗︼級
	 * @param hexStr
	 * @return
	 */
	public static int HexStr2Int(String hexStr) {
		hexStr = hexStr.replaceAll("^0[x|X]", "");
		
		if(hexStr.length() > 8)
			throw new IndexOutOfBoundsException();
		
		int result = 0, length = hexStr.length();
		for(int i=0; i<hexStr.length(); i++) {
			int digit = Character.digit(hexStr.charAt(i), 16);
			int next = result * 16 - digit;
			result = next;
		}

		return -result;
	}
}
