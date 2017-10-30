package com.ea.model.hele.transport;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

public class Fee {
	@Resource
	private Transfee transfee;
	private List<String> goodsId = new ArrayList<String>();
	private List<Transfee> transfeeList = new ArrayList<>();

	public Fee() {
		this.transfeeList.add(transfee);
	}

	public List<String> getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(List<String> goodsId) {
		this.goodsId = goodsId;
	}

	public List<Transfee> getTransfee() {
		return transfeeList;
	}

	public void setTransfee(List<Transfee> transfee) {
		this.transfeeList = transfee;
	}

}
