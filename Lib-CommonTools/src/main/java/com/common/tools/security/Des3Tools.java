package com.common.tools.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Des3Tools {

	private static final String Algorithm = "DES"; // 定义 加密算法,可用
														// DES,DESede,Blowfish

	// // keybyte为加密密钥，长度为24字节
	// // src为被加密的数据缓冲区（源）
	// public static byte[] encryptMode(byte[] keybyte, byte[] src) {
	// try {
	// // 生成密钥
	// SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
	//
	// // 加密
	// Cipher c1 = Cipher.getInstance(Algorithm);
	// c1.init(Cipher.ENCRYPT_MODE, deskey);
	// return c1.doFinal(src);
	// } catch (java.security.NoSuchAlgorithmException e1) {
	// e1.printStackTrace();
	// } catch (javax.crypto.NoSuchPaddingException e2) {
	// e2.printStackTrace();
	// } catch (java.lang.Exception e3) {
	// e3.printStackTrace();
	// }
	// return null;
	// }
	//
	// // keybyte为加密密钥，长度为24字节
	// // src为加密后的缓冲区
	// public static byte[] decryptMode(byte[] keybyte, byte[] src) {
	// try {
	// // 生成密钥
	// SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
	//
	// // 解密
	// Cipher c1 = Cipher.getInstance(Algorithm);
	// c1.init(Cipher.DECRYPT_MODE, deskey);
	// return c1.doFinal(src);
	// } catch (java.security.NoSuchAlgorithmException e1) {
	// e1.printStackTrace();
	// } catch (javax.crypto.NoSuchPaddingException e2) {
	// e2.printStackTrace();
	// } catch (java.lang.Exception e3) {
	// e3.printStackTrace();
	// }
	// return null;
	// }

	// 转换成十六进制字符串
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ":";
		}
		return hs.toUpperCase();
	}

	/**
	 * ECB加密,不要IV
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static byte[] des3EncodeECB(byte[] key, byte[] data)
			throws Exception {

		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");

		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] bOut = cipher.doFinal(data);

		return bOut;
	}

	/**
	 * ECB解密,不要IV
	 * 
	 * @param key
	 *            密钥
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] ees3DecodeECB(byte[] key, byte[] data)
			throws Exception {

		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/ECB/PKCS5Padding");

		cipher.init(Cipher.DECRYPT_MODE, deskey);

		byte[] bOut = cipher.doFinal(data);

		return bOut;

	}

	/**
	 * CBC加密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            明文
	 * @return Base64编码的密文
	 * @throws Exception
	 */
	public static byte[] des3EncodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {

		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] bOut = cipher.doFinal(data);

		return bOut;
	}

	/**
	 * CBC解密
	 * 
	 * @param key
	 *            密钥
	 * @param keyiv
	 *            IV
	 * @param data
	 *            Base64编码的密文
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] des3DecodeCBC(byte[] key, byte[] keyiv, byte[] data)
			throws Exception {

		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key);
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance("desede" + "/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(keyiv);

		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

		byte[] bOut = cipher.doFinal(data);

		return bOut;
	}

	public static void main(String[] args) throws Exception {

		byte[] key = Base64Tools.decode("YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4");
		byte[] keyiv = { 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8 };

		byte[] data = "中国ABCabc123".getBytes("UTF-8");

		System.out.println("ECB加密解密");
		byte[] str3 = des3EncodeECB(key, data);
		byte[] str4 = ees3DecodeECB(key, str3);
		System.out.println(Base64Tools.encode(str3));
		System.out.println(new String(str4, "UTF-8"));

		System.out.println();

		System.out.println("CBC加密解密");
		byte[] str5 = des3EncodeCBC(key, keyiv, data);
		byte[] str6 = des3DecodeCBC(key, keyiv, str5);
		System.out.println(Base64Tools.encode(str5));
		System.out.println(new String(str6, "UTF-8"));

	}
	
	/**
	 * 
	 * @描述: 3DES 加密
	 * @param key
	 * @param src
	 * @return
	 */
	public static byte[] enTripleDES(byte[] key, byte[] src) {

		byte[] keyleft = new byte[8];
		byte[] keyright = new byte[8];
		System.arraycopy(key, 0, keyleft, 0, 8);
		System.arraycopy(key, 8, keyright, 0, 8);

		byte[] leftencrypt1 = encryptMode(keyleft, src);
		byte[] rightdecrypt2 = decryptMode(keyright, leftencrypt1);
		byte[] leftencrypt3 = encryptMode(keyleft, rightdecrypt2);
		return leftencrypt3;
	}
	
	/**
	 * 
	 * @描述: 3DES 解密
	 * @param key
	 * @param src
	 * @return
	 */
	public static  byte[] deTripleDES(byte[] key, byte[] src){
		byte[] keyleft = new byte[8];
		byte[] keyright = new byte[8];
		System.arraycopy(key, 0, keyleft, 0, 8);
		System.arraycopy(key, 8, keyright, 0, 8);
	
		byte[] leftencrypt1 = decryptMode(keyleft, src);
		byte[] rightdecrypt2 = encryptMode(keyright, leftencrypt1);
		byte[] leftencrypt3 = decryptMode(keyleft, rightdecrypt2);
		
		return leftencrypt3;
		
	}	


	/**
	 * 用于des加密数据的填充
	 * 
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	public static byte[] extendMsgForDes(byte[] msg) throws Exception {
		int msgLen = 0;
		int respLen = 0;

		msgLen = msg.length;
		// 确定报文的长度，必须是8的倍数，不足补0x00
		if (msgLen % 8 != 0) {
			respLen = (msgLen / 8 + 1) * 8;
		} else {
			respLen = msgLen;
		}
		byte[] respMsg = new byte[respLen];
		System.arraycopy(msg, 0, respMsg, 0, msgLen);

		// 补0x00
		for (int i = msgLen; i < respLen; i++) {
			respMsg[i] = 0x00;
		}

		return respMsg;
	}

	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm + "/ECB/NoPadding");
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(keybyte, "DES");

			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm + "/ECB/NoPadding");
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

}
