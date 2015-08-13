package com.common.tools.security;

public class CRC16 {
	public static final char[] ascii = "0123456789ABCDEF".toCharArray();

	public static byte[] short2bytes(int num) {
		byte[] b = new byte[2];
		//int mask = 0xff;
		for (int i = 0; i < 2; i++) {
			b[i] = (byte) (num >>> (8 - i * 8));
		}
		return b;
	}
	
	public static String bcdToString(byte[] bcds) {
		if (bcds == null || bcds.length == 0) {
			return null;
		}
		byte[] temp = new byte[2 * bcds.length];
		for (int i = 0; i < bcds.length; i++) {
			temp[i * 2] = (byte) ((bcds[i] >> 4) & 0x0f);
			temp[i * 2 + 1] = (byte) (bcds[i] & 0x0f);
		}
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < temp.length; i++) {
			res.append(ascii[temp[i]]);
		}
		return res.toString();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//byte[] obj = {01,02,03,04};
		//byte[] obj = {01,02,03,04,02,03,04,01,02,03,04,02,03,04,0x0a,0x0b,0x0c,0x0d};
		
		//byte[] obj = {1,2,3,4,5,6,7,8,9,03,04,02,03,04,0x0a,0x0b,0x0c,0x0d};
		byte[] obj = new byte[256];
		for (int i = 0; i<256; i++){
			obj[i] = (byte)i;
		}
		System.out.print(bcdToString(obj));
		byte[] res = cal_crc16(obj, obj.length);
		System.out.print("\n");
		System.out.print(bcdToString(res));
		
	}
	
	public static byte [] cal_crc16(byte[] ptr, int len)
	{
		int crc;
		byte da;
		int[] crc_ta = {
			// CRC 余式表
			0x0000, 0x1021, 0x2042, 0x3063,	
			0x4084, 0x50a5, 0x60c6, 0x70e7,
			0x8108, 0x9129, 0xa14a, 0xb16b,
			0xc18c, 0xd1ad, 0xe1ce, 0xf1ef,
		};

		crc = 0;
		int i = 0;
		while (len-- != 0)
		{
			da = (byte)(((crc>>8)&0x0ff) >> 4); 
			crc <<= 4;
			crc ^= crc_ta[(da & 0x0f) ^ ((ptr[i] >> 4) & 0x0f)];
			da = (byte)(((crc>>8)&0x0ff) >> 4);
			crc <<= 4;
			
			crc ^= crc_ta[(da & 0x0f)  ^ (ptr[i] & 0x0f)];
			i++;
		}
		
		return short2bytes(crc);
	}  
}
