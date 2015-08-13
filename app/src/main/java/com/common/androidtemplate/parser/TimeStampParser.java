package com.common.androidtemplate.parser;


import com.common.androidtemplate.bean.TimeStamp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class TimeStampParser {
	public TimeStamp parse(String resultStr) {
		
		Gson gson = new Gson();
		TimeStamp data = gson.fromJson(resultStr, new TypeToken<TimeStamp>(){}.getType());
		
		return data;
	}
}