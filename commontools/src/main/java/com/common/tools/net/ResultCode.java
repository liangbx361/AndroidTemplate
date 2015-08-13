package com.common.tools.net;

public interface ResultCode {
	
	/**
	 * 成功
	 */
	public static final int RESULT_SUCCESS = 0;
	public static final String RESULT_SUCCESS_DESC = "成功";
	
	/**
	 * 请求格式错误
	 */
	public static final int RESULT_REQUEST_ERROR = 1;
	
	/**
	 * 时间格式错误
	 */
	public static final int RESULT_DATE_ERROR = 2;
	
	/**
	 * 结果为空
	 */
	public static final int RESULT_NULL = 3;
	
	/**
	 * 系统内部错误
	 */
	public static final int RESULT_SYSTEM_ERROR= 4;
	
	/**
	 * 无网络连接
	 */
	
	public static final int NO_NETWORK = 5;
	public static final String NO_NETWORK_DESC = "无网络连接，请检查网络";
	
	/**
	 * 连接服务器失败 
	 */
	public static final int CONTENT_SERVER_ERROR = 6;
	public static final String CONTENT_SERVER_ERROR_DESC = "服务器连接异常";
	
	/**
	 * 脱机登录成功
	 */
	public static final int OFFLINE_LOGIN_SUCCESS = 7;
	
	/**
	 * 连接正常
	 */
	public static final int CONTENT_SERVER_OK = -1;
	
	/**
	 * 用户不存在
	 */
	public static final int RESULT_NO_USERNAME = 101;
	public static final String RESULT_NO_USERNAME_DESC = "用户名不存在";
	
	/**
	 * 数据解析失败
	 */
	public static final int DATA_PARSER_FAIL = 8;
	public static final String DATA_PARSER_FAIL_DESC = "数据解析失败";
	
	/**
	 * 响应数据错误
	 */
	public static final int REQ_DATA_FAIL = 9;
	
	/**
	 * 结果数为0
	 */
	public static final int RESULT_ZERO = 10;
}
