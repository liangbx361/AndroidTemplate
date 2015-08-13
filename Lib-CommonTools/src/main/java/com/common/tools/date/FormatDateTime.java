package com.common.tools.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.text.Editable;
/**
 * 
 * @Title:
 * 
 * @Description:
 * 
 * @Copyright: Copyright (c) 2007 FFCS All Rights Reserved
 * 
 * @Company: 北京福富软件有限公司
 * 
 * @author 钟敏鑫 2007-10-25 17:16:56
 * @version 1.00.000
 * @history:
 * 
 * 郑智  2007-10-25 
 * 添加 getCurrentYear 方法
 * 
 * guojq 2007-11-12
 * 添加 getCurrentMonth 方法
 */
public final class FormatDateTime
{	
    public final static String DATE_YM = "yyyy-MM";
	public final static String DATE_YMD = "yyyy-MM-dd";
	public final static String DATE_YMDH = "yyyy-MM-dd HH";
	public final static String DATETIME_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public final static String DATETIME_YMDHM = "yyyy-MM-dd HH:mm";
	public final static String TIME_HMS = "HH:mm:ss";
	public final static String DefaultSmallTime = "00:00:00";
	public final static String DefaultBigTime = "23:59:59";
	public final static String DATETIME_YMDHMS_STR="yyyyMMddHHmmss";
    public final static String DATETIME_YYMDHMS_STR="yyMMddHHmmss";
    public final static String DATETIME_YYMDH_STR="yyMMddHH";
	public final static String DATE_MD ="MMdd";
    public final static String DATE_YYMMDD ="yyMMdd";
	public final static String TIME_HHMMSS ="HHmmss";
	public final static String DATE_YMD_STR="yyyyMMdd";
	public final static String DATETIME_YMDHMSS_STR="yyyyMMddHHmmssS";
	public final static String DATE_YM_STR="yyyyMM";
	
	
	private FormatDateTime(){}
	/**
	 * 
	 * @description    获取当前日期
	 * @return
	 */
	public static String getCurrDate()
	{
		return SimpleDateFormat.getDateInstance().format(new Date());
	}
	
	/**
	 * @description    获取当前日期
	 * @return
	 */
	public static Date getCurrentDate() {
		String strDate = SimpleDateFormat.getDateInstance().format(new Date());
		Date date = null;
		try{
			date = new SimpleDateFormat("yyyy-MM-dd").parse(strDate);
		}catch(ParseException pe){
			pe.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static Date getCurrentDateTime(){
		return string2DateTime2(getCurrDateTime());
	}
	
	/**
	 * 
	 * @description    获取当前时间
	 * @return
	 */
	public static String getCurrTime()
	{
		return SimpleDateFormat.getTimeInstance().format(new Date());
	}
	/**
	 * 
	 * @description    获取当前日期时间
	 * @return
	 */
	public static String getCurrDateTime()
	{
		return SimpleDateFormat.getDateTimeInstance().format(new Date());
	}
	/**
	 * 
	 * @description    字符型日期转换成日期型
	 * @param ref_date
	 * @return
	 */
	public static Date string2Date(String ref_date)
	{
		try
		{
			return new SimpleDateFormat(DATE_YMD).parse(ref_date);
		}
		catch(ParseException pe)
		{
			return null;
		}
	}
	/**
	 * 
	 * @description    字符型日期时间转换成日期型
	 * @param ref_dt
	 * @return
	 */
	public static Date string2DateTime(String ref_dt)
	{
		try
		{
			return new SimpleDateFormat(FormatDateTime.DATETIME_YMDHMS).parse(ref_dt);
		}
		catch(ParseException pe)
		{
			return null;
		}
	}
	
	/**
	 * 字符型日期时间转换成日期型	 * @param ref_dt
	 * @return
	 */
	public static Date string2DateTime2(String ref_dt)
	{
		try
		{
			return new SimpleDateFormat(FormatDateTime.DATETIME_YMDHM).parse(ref_dt);
		}
		catch(ParseException pe)
		{
			return null;
		}
	}
	
	/**
	 * @description    字符型日期时间转换成日期型
	 * @param ref_date
	 * @param ref_format
	 * @return
	 */
	public static Date string2Date(String ref_date, String ref_format)
	{
	    if(ref_date == null)
	        return null;
		try
		{
			return new SimpleDateFormat(ref_format).parse(ref_date);
		}
		catch(Exception e)
		{
            e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @description    日期型日期转换成字符型
	 * @param ref_date
	 * @return
	 */
	public static String date2String(Date ref_date)
	{
		return new SimpleDateFormat(DATE_YMD).format(ref_date);
	}
	/**
	 * 
	 * @description    日期型日期转换成指定格式字符型	 * @author 庄志明   2006-8-29
	 * @param ref_date
	 * @param ref_format
	 * @return
	 */
	public static String date2String(Date ref_date, String ref_format)
	{
		try
		{
			return new SimpleDateFormat(ref_format).format(ref_date);
		}
		catch(Exception e)
		{
			return date2String(ref_date);
		}
	}
	/**
	 * 
	 * @description    日期型日期时间转换成字符型
	 * @param ref_date
	 * @return
	 */
	public static String dateTime2String(Date ref_date)
	{
		return new SimpleDateFormat(DATETIME_YMDHMS).format(ref_date);
	}
	/**
	 * 
	 * @description    日期型日期时间转换成指定格式字符型
	 * @param ref_date
	 * @param ref_format
	 * @return
	 */
	public static String dateTime2String(Date ref_date, String ref_format)
	{
		try
		{
			return new SimpleDateFormat(ref_format).format(ref_date);
		}
		catch(Exception e)
		{
			return dateTime2String(ref_date);
		}
	}
	 
	  
	 /**
	  * 
	  * @description:取得指定日期若干天后的日期
	  * @param thisDate 指定日期
	  * @param addDays  若干天后
	  * @return
	  */
	   
	  public static String getNextDay(String thisDate, String addDays) {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	      Date date = formatter.parse(thisDate);
	      cal.setTime(date);
	      cal.add(Calendar.DATE, Integer.parseInt(addDays));
	      String nextDay = formatter.format(cal.getTime());
	      return nextDay;
	    }
	    catch (ParseException e) {
	      return "";
	    }
	  }
	  
	  public static String getNextDay(Date date, String days,String format) {
	        Calendar cal = Calendar.getInstance();
	        SimpleDateFormat formatter = new SimpleDateFormat(format);
	        try {
	          cal.setTime(date);
	          cal.add(Calendar.DATE, Integer.parseInt(days));
	          String nextDay = formatter.format(cal.getTime());
	          return nextDay;
	        }
	        catch (Exception e) {
	          return "";
	        }
	      }
	  
	  public static String getNextHour(Date date, String hour,String format) {
          Calendar cal = Calendar.getInstance();
          SimpleDateFormat formatter = new SimpleDateFormat(format);
          try {
            cal.setTime(date);
            cal.add(Calendar.HOUR, Integer.parseInt(hour));
            String nextDay = formatter.format(cal.getTime());
            return nextDay;
          }
          catch (Exception e) {
            return "";
          }
        }
	  /**
	   * @description:取得指定日期若干月后的日期
	   * @param thisDate
	   * @param addMonths
	   * @return
	   */
	  public static String getNextMonth(String thisDate, String addMonths) {
		    Calendar cal = Calendar.getInstance();
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		    try {
		      Date date = formatter.parse(thisDate);
		      cal.setTime(date);
		      cal.add(Calendar.MONTH, Integer.parseInt(addMonths));
		      String nextDay = formatter.format(cal.getTime());
		      return nextDay;
		    }
		    catch (ParseException e) {
		      System.out.println("Unable to parse " + thisDate);
		      return "";
		    }
		  }
	  public static int daysBetweenDate(Date date1, Date date2)
		{
			long days = Math.abs(date1.getTime() - date2.getTime());
			int int_days = (int)(days/(1000*60*60*24));
			return int_days;
		} 
	  
	  public static String getToday ( String pattern ){
	        Date date = new Date () ;
	        SimpleDateFormat sdf = new SimpleDateFormat ( pattern ) ;
	        return sdf.format ( date ) ;
	    }
	  
	  public static String getCurrMFirstDay() {
		  String strCurrDay = FormatDateTime.date2String(new Date());
		  
		  return strCurrDay.substring(0,8)+"01";
	  }
	  
	  /**
		 * 获取当前年份
		 * @return
		 */
		public static String getCurrentYear(){
			Calendar c = new GregorianCalendar();
			return ""+c.get(Calendar.YEAR);
		}
		
		/**
		 * 获取当前月		 * @return
		 */
		public static String getCurrentMonth(){
			Calendar c = new GregorianCalendar();
			return ""+(c.get(Calendar.MONTH)+1);
		}
		
		/**
	     * 获取前n个月的时间
	     * 
	     * @param date
	     * @param n
	     * @return
	     */
	    public static Date getSeveralMonthsBefore(Date date, int n) {
	        Calendar c = new GregorianCalendar();
	        c.setTime(date);
	        c.add(Calendar.MONTH, -n);
	        return c.getTime();
	    }
	    
	    public static String lastDayOfMonth2(String thisDate,String format){
	        Calendar cal = Calendar.getInstance();
	        SimpleDateFormat formatter = new SimpleDateFormat(format);
	        try {
	            Date date = formatter.parse(thisDate);
	            cal.setTime(date);
	            int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	            cal.set(Calendar.DAY_OF_MONTH, value);
	           
	            return ""+value;
	            
	        } catch (ParseException e) {
	            System.out.println("Unable to parse " + thisDate);
	            return "";
	        }
	        
	    }
	 
	/**
	 * test
	 */
	/*public static void test()
	{
		System.out.println(getCurrDate());
		System.out.println(getCurrTime());
		System.out.println(getCurrDateTime());
		System.out.println(string2Date("2006-08-19"));
		System.out.println(date2String(new Date()));
		System.out.println(date2String(new Date(),null));
		System.out.println(dateTime2String(new Date()));
		System.out.println(dateTime2String(new Date(),"MM/dd/yyyy HH:mm:ss"));
		System.out.println(string2DateTime("Wed Sep 20 14:26:11 CST 2006"));
		System.out.println(getNextDay("2006-10-31","1"));
		System.out.println("get current Date :"+getCurrentDate().toString());
		
	}
	*//**
	 * only for testing
	 * @param args
	 *//*
	public static void main(String[] args)
	{
		test();
	}*/
		
//		public static void main(String[] args) {
//        System.out.println(FormatDateTime.getNextDay("2010-11-11","-10"));
//        System.out.println(FormatDateTime.getNextDay("2010-11-11","-30"));
//        System.out.println(FormatDateTime.getNextDay(new Date(),"-22","yyyyMMdd"));
//        System.out.println(FormatDateTime.date2String(new Date(), FormatDateTime.DATETIME_YMDHMSS_STR));
//        
//		}
	
	/**
	 * yyMMdd - yy.mm.dd    
	 */
	public static String formatY_M_D(String time) {
		Editable et = Editable.Factory.getInstance().newEditable(time);
		et.append(".", 4, 5);
		et.append(".", 6, 7);
		return et.toString();
	}
}
