package com.common.tools.verify;

import java.util.regex.Pattern;

/**
 * 
 * @描述：邮箱格式验证
 * @作者：liang bao xian
 * @时间：2014年8月1日 上午11:52:32
 */
public class EmailVerify {
	
	public static boolean isEmail(String email) {
		Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		return p.matcher(email).matches();
	}
}
