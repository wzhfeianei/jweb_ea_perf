package com.ea.trade.thirdpart.hele.impl;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ea.trade.thirdpart.hele.HeleCommonInterface;
import com.ea.trade.thirdpart.hele.utils.MakeRSASign;
import com.ea.trade.util.HttpPostUtil;



@Component
public class HeleCommonInterfaceImpl implements HeleCommonInterface {
	
	
	@Value("${hele.privateKey}")
	private String privateKey;
	
	@Value("${hele.url}")
	private String url;
	
	@Override
	public Map<String,String> getSign(String source) throws UnsupportedEncodingException{
		
		return MakeRSASign.getSign(source, privateKey);
		
	}

	@Override
	public String postData(String content) {

		return HttpPostUtil.post(url, content);
		
	}

	@Override
	public String decode(String source) throws UnsupportedEncodingException {
	  return MakeRSASign.decode(source, privateKey);
	}
	
	
	
	
}
