package com.ea.model.hele;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
	private String orderCode;
	private String heleOrderCode;

	public Order(String orderCode) {
		this.orderCode = orderCode;
		this.heleOrderCode = "00_" + orderCode + "_72_" + new SimpleDateFormat("yyMMddHHmm").format(new Date());

	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getHeleOrderCode() {
		return heleOrderCode;
	}

	public void setHeleOrderCode(String heleOrderCode) {
		this.heleOrderCode = heleOrderCode;
	}

}
