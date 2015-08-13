package com.common.tools.verify;

import java.util.regex.Pattern;

/**
 * 
 * @描述：身份证号码格式验证
 * @作者：liang bao xian
 * @时间：2014年8月1日 上午11:37:42
 */
public class IdentityCardVerify {
	
	public boolean isIdCard(String number) {
		boolean isIdCard = false;
		Pattern p15 = Pattern.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
		Pattern p18 = Pattern.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
		
		if(p15.matcher(number).matches() || p18.matcher(number).matches()) {
			isIdCard = true;
		}
		
		return isIdCard;
	}
}
