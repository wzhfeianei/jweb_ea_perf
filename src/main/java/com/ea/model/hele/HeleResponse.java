package com.ea.model.hele;

import java.util.List;

public class HeleResponse {
	private String code = "1";
	private String msg = "";
	private List<Object> result;

	public HeleResponse() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Object> getResult() {
		return result;
	}

	public void setResult(List<Object> result) {
		this.result = result;
	}

}
