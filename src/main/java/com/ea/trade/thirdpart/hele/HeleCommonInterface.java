package com.ea.trade.thirdpart.hele;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 第三方和乐网 通用的接口 
 * 
 * @author livi
 *
 */
public interface HeleCommonInterface {
	
	/**
	 * 获取签名后的数据
	 * 
	 * @param source
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public Map<String,String> getSign(String source) throws UnsupportedEncodingException;
	
    /**
     * 发送数据到和乐网
     * 
     * @param content
     * @return
     */
	public String postData(String content);
	
	/**
	 * 
	 * 对和乐网返回的数据解密
	 * 
	 * @param source
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String decode(String source) throws UnsupportedEncodingException;
	
}
