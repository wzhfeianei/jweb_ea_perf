package com.ea.trade.util;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 
 * 
 * Http Post 请求工具类
 * 
 * 
 * @author Livi
 *
 */
public class HttpPostUtil {
	
	
	private  static RequestConfig   requestConfig;
	    
	private  static CloseableHttpClient httpClinet;
	    
	private static final Logger  log = LoggerFactory.getLogger(HttpPostUtil.class);
	    
	static {
		
//		String env = EnvManager.getEnv();
//		Properties prop = new Properties();   
//	    InputStream in = HttpPostUtil.class.getResourceAsStream("/cfg_"+env+".properties");   
//        try {
//			prop.load(in);
//			String  param1 = prop.getProperty("wecat.mch_id").trim();   
//	        System.out.println("param1:::::"+param1);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}   
		
		requestConfig  = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(3000).build();
		httpClinet = HttpClients.custom().build();   
	}
	
	/**
	 * 
	 * 
	 * @param url  
	 * @param requestStr
	 * @return
	 */
	public static String post(String url,String requestStr){
		
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		log.info("request data=="+requestStr);
		StringEntity postEntity = new StringEntity(requestStr, "UTF-8");
	        httpPost.addHeader("Content-Type", "text/xml");
	        httpPost.setEntity(postEntity);
	        httpPost.setConfig(requestConfig);
	        
	        log.info("executing request" + httpPost.getRequestLine());

	        try {
	            HttpResponse response = httpClinet.execute(httpPost);
	            
	            if(HttpStatus.SC_OK!=response.getStatusLine().getStatusCode()){
                     return "";
	            	//return "<xml><return_code>FAIL</return_code><return_msg><![CDATA[ HttpResponse error , StatusCode : "+response.getStatusLine().getStatusCode()+"]]></return_msg></xml>";
	            }
	            
	            HttpEntity entity = response.getEntity();

	            result = EntityUtils.toString(entity, "UTF-8");

	        } catch (ConnectionPoolTimeoutException e) {
	            log.error(e.getMessage(),e);
	        	log.error("http get throw ConnectionPoolTimeoutException(wait time out)");

	        } catch (ConnectTimeoutException e) {
	        	log.error(e.getMessage(),e);
	        	log.error("http get throw ConnectTimeoutException");

	        } catch (SocketTimeoutException e) {
	        	 log.error(e.getMessage(),e);
	        	log.error("http get throw SocketTimeoutException");

	        } catch (Exception e) {
	        	 log.error(e.getMessage(),e);
	        	log.error("http get throw Exception");

	        } finally {
	            httpPost.abort();
	        }
		
	        log.info("result:"+result);
		
		return result;
	}
	
	
	public static String post(String url,Map<String,String> parameterMap){
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		for(Entry<String, String>   entry :   parameterMap.entrySet()){
			nameValuePairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		}

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPost.setConfig(requestConfig);
			log.info("url:{},parameterMap:{}", url, JSON.toJSON(parameterMap));
		    HttpResponse response = httpClinet.execute(httpPost);
			log.info("httpStatus:{}", response.getStatusLine().getStatusCode() );
            if(HttpStatus.SC_OK!=response.getStatusLine().getStatusCode()){
            	return "";
            }
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
			log.info("result:{}", result );
		} catch (Exception e) {
            //log.error(e.getMessage(),e);
		} finally {
			httpPost.abort();
		}
		return result;
	}
	
	public static String post(String url,Map<String,String> parameterMap, String Encoder){
		
		String result = null;
		HttpPost httpPost = new HttpPost(url);
		
		List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>(); 
		
		for(Entry<String, String>   entry :   parameterMap.entrySet()){
			nameValuePairs.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		}
		
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,Encoder));
			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPost.setConfig(requestConfig);
			HttpResponse response = httpClinet.execute(httpPost);
			
			if(HttpStatus.SC_OK!=response.getStatusLine().getStatusCode()){
				return "";
			}
			
			HttpEntity entity = response.getEntity();
			
			result = EntityUtils.toString(entity, "UTF-8");
			
		} catch (Exception e) {
			//log.error(e.getMessage(),e);
		} finally {
			httpPost.abort();
		}
		
		return result;
	}
	
	

}
