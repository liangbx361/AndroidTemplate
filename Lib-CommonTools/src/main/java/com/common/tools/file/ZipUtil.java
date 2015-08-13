package com.common.tools.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipInputStream;

public class ZipUtil {
	
	/**
	 * 
	 * @描述: zip 数据解压
	 * @param bContent
	 * @return
	 */
	public static byte[] unZip(byte[] bContent) {
		byte[] b = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bContent);
			ZipInputStream zip = new ZipInputStream(bis);
			
			while (zip.getNextEntry() != null) {
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = zip.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				b = baos.toByteArray();
				baos.flush();
				baos.close();
			}
			
			zip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return b;
	}
}
