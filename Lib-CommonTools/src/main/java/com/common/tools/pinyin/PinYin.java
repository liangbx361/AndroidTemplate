package com.common.tools.pinyin;

import java.util.ArrayList;

import com.common.tools.pinyin.HanziToPinyin.Token;

public class PinYin {
	
	public static final int TYPE_LOWER_CASE = 0;
	public static final int TYPE_UP_CASE = 1;
	public static final int TYPE_FIRST_UP_CASE = 2;
	
	//汉字返回拼音，字母原样返回，都转换为小写
	public static String getPinYin(String input, int type) {
		ArrayList<Token> tokens = HanziToPinyin.getInstance().get(input);
		StringBuilder sb = new StringBuilder();
		if (tokens != null && tokens.size() > 0) {
			for (Token token : tokens) {				
				if (Token.PINYIN == token.type) {
					switch(type) {
					case TYPE_FIRST_UP_CASE:
						String firstChar = token.target.substring(0, 1);
						sb.append(firstChar + token.target.substring(1).toLowerCase());
						break;
					case TYPE_LOWER_CASE:
						sb.append(token.target.toLowerCase());
						break;						
					}
				} else {
					sb.append(token.source);
				}
			}
		}
		return sb.toString();
	}
}
