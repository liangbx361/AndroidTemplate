package com.common.tools.net;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import android.net.Uri;

public class HttpHelper {
	
	private static final String TAG = "HttpHelper";
	
	public static final int TIMEOUT_DETLAY = 6000;
	
	/**
	 * Http GET请求方式
	 * @param url
	 * @param params
	 * @param processer
	 * @return
	 */
	public static int get(String url, Map<String,String> params, ProcesserJson processer){
		HttpResponse httpResponse = null;
		 
		String query="";
		String[] keys = params.keySet().toArray(new String[] {});
		for(int i = 0, size  = keys.length; i < size; i++) {
			String key = keys[i];
			String value = params.get(key).replaceAll("\n", "");
			query += (i == 0 ? "?" : "&");
			query += key;
			query += "=";
			query += Uri.encode(value);
		}
		
		url += query;
		
		HttpGet httpGet = new HttpGet(url);
		httpGet.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, TIMEOUT_DETLAY);
		httpGet.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, TIMEOUT_DETLAY);
			
		 try {
			httpResponse = new DefaultHttpClient().execute(httpGet);
//			httpResponse.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, TIMEOUT_DETLAY);
//			httpResponse.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, TIMEOUT_DETLAY);
			
			if (httpResponse.getStatusLine().getStatusCode() == 200)  {  
				HttpEntity entity = httpResponse.getEntity();
				String jsonStr = EntityUtils.toString(entity, HTTP.UTF_8);
					
				return processer.execute(jsonStr);
			} else {
				HttpEntity entity = httpResponse.getEntity();
				String jsonStr = EntityUtils.toString(entity, HTTP.UTF_8);
				return ResultCode.CONTENT_SERVER_ERROR;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return ResultCode.CONTENT_SERVER_ERROR;
		} catch (IOException e) {
			e.printStackTrace();
			return ResultCode.CONTENT_SERVER_ERROR;
		}
	}
	
	/**
	 * post 请求方式
	 * @param url
	 * @param params
	 * @param processer
	 * @return
	 */
	public static int post(String url, Map<String,String> params, ProcesserJson processer){
		
		HttpClient httpClient = new DefaultHttpClient(); 
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// 请求超时
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT_DETLAY);
        // 读取超时
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, TIMEOUT_DETLAY);
		
		try {
			if(params != null){
				for(String key : params.keySet()){
					nvps.add(new BasicNameValuePair(key, params.get(key)));
				}
			}

			httpPost.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));		
			HttpResponse response = httpClient.execute(httpPost);
			
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();				
				String jsonStr = EntityUtils.toString(entity, HTTP.UTF_8);
					
				return processer.execute(jsonStr);
			} else {
				return ResultCode.CONTENT_SERVER_ERROR;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return ResultCode.CONTENT_SERVER_ERROR;
		}
		
	}
		
	/**
	 * 写流的方式
	 * @param xmlData
	 * @param processer
	 * @return
	 */
	public static int URLConnection(String urlStr, String xmlData, ProcesserXml processer) {
		String result = "";
		HttpURLConnection con = null;
		InputStream inStrm = null;
		try {
		
			URL url = new URL(urlStr);
			
			con = (HttpURLConnection)url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");
			con.setConnectTimeout(3000);
	
			OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(),"UTF-8");    
	
			out.write(xmlData);
			out.flush();
			out.close();
			
			inStrm = con.getInputStream(); 
		    
		    InputStreamReader ir = new InputStreamReader(inStrm,"UTF-8");
		    BufferedReader bsr = new BufferedReader(ir);
		    
		    String line = "";
		    while((line = bsr.readLine()) != null){
		    	result += line + "\n";
		    }
		    
		    ir.close();
		    bsr.close();
		    inStrm.close();
		    con.disconnect();
		    
		    //用DOM解析XML
			InputStream is = new ByteArrayInputStream(result.getBytes("UTF-8"));
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document document = docBuilder.parse(is);
			
		    return processer.execute(document.getDocumentElement());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} 
		
		return ResultCode.CONTENT_SERVER_ERROR;
	}
	
	/**
	 * 检测URL是否有效
	 * @param url
	 * @return
	 */
	public static boolean checkURL(String url){
		boolean value=false;
		try {
			HttpURLConnection conn=(HttpURLConnection)new URL(url).openConnection();
			int code=conn.getResponseCode();
		
			if(code!=200){
				value=false;
			}else{
				value=true;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {		
			e.printStackTrace();
		}
		return value;
	}
	
	//解析xml
	public interface ProcesserXml{
		public int execute(Element root);
	}
	
	//解析json
	public interface ProcesserJson {
		public int execute(String json);
	}
}
