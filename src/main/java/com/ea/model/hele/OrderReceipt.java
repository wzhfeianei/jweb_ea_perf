package com.ea.model.hele;

public class OrderReceipt extends Order {
	private String status = "1";
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	private String orderStatus = "40";

	public OrderReceipt(String orderCode) {
		super(orderCode);
		// TODO Auto-generated constructor stub
	}

}
