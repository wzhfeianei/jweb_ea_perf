package com.ea.sms;

import com.ea.model.IdlResp;

public class SendSmsResp extends IdlResp{
	
	String code;
	
	String msg;

	
	public String getCode() {
		return code;
	}

	
	public void setCode(String code) {
		this.code = code;
	}

	
	public String getMsg() {
		return msg;
	}

	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
