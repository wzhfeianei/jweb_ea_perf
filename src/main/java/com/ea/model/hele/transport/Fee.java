package com.ea.model.hele.transport;

import java.util.ArrayList;
import java.util.List;

public class Fee {
	private List<String> goodsId= new ArrayList<String>();
	private List<Transfee> transfee= new ArrayList<>();
	
	public Fee() {
		this.transfee.add(new Transfee());
	}

	public List<String> getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(List<String> goodsId) {
		this.goodsId = goodsId;
	}

	public List<Transfee> getTransfee() {
		return transfee;
	}

	public void setTransfee(List<Transfee> transfee) {
		this.transfee = transfee;
	}

}
