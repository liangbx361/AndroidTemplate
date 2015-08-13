package com.common.tools.verify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 
 * @描述：验证手机格式是否正确
 * @作者：liang bao xian
 * @时间：2014年8月1日 上午11:16:24
 */
public class MobileVerify {
	
	/**
	 * 验证手机号是否正确
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(14[5,7])|(17[0,6-8])|(18[^4,\\D]))\\d{8}$");
		Matcher m = p.matcher(mobiles);

		return m.matches();
	}
}
