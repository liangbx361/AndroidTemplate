package com.common.tools.file;

import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;

public class AssetsUtil {

	public static String getStringFromFile(Context context, String fileName) {
		String result = "";
		try {
			InputStream in = context.getResources().getAssets().open(fileName);
			// 获取文件的字节数
			int lenght = in.available();
			// 创建byte数组
			byte[] buffer = new byte[lenght];
			// 将文件中的数据读到byte数组中
			in.read(buffer);
			result = EncodingUtils.getString(buffer, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
