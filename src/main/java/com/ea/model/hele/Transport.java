package com.ea.model.hele;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.ea.model.hele.transport.Fee;

public class Transport {
	@Resource
	private Fee f;
	private String hele_supplier_id;
	private String seller_id;
	private List<Fee> fee = new ArrayList<>();

	public Transport() {
		this.fee.add(f);
	}

	public List<Fee> getFee() {
		return fee;
	}

	public void setFee(List<Fee> fee) {
		this.fee = fee;
	}

	public String getHele_supplier_id() {
		return hele_supplier_id;
	}

	public void setHele_supplier_id(String hele_supplier_id) {
		this.hele_supplier_id = hele_supplier_id;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public static void main(String[] args) {
		// HeleResponse heleres = new HeleResponse();
		// Transport t = new Transport();
		// List<Object> list = new ArrayList<>();
		// List<Object> leelist = new ArrayList<>();
		// List<Object> leetransfeelist = new ArrayList<>();
		// heleres.setMsg("dasdf");
		// heleres.setCode("1");
		// t.setHele_supplier_id("44");
		// t.setSeller_id("17556");
		//
		// Fee fee = new Fee();
		// List<String> goodslistid = new ArrayList<>();
		// goodslistid.add("42598");
		// fee.setGoodsid(goodslistid);
		//
		// Transfee transfee = new Transfee();
		// List<Transfee> listTransfee = new ArrayList<>();
		// listTransfee.add(transfee);
		// fee.setTransfee(listTransfee);
		// leelist.add(fee);
		// t.setFee(leelist);
		// list.add(t);
		// heleres.setResult(list);
		// System.out.println(JSON.toJSONString(heleres));
		// String requestBody
		// =[{"areaId":"4524163","cityId":"4524157","goodsInfo":[{"goodsId":"42598","goods_spec_id":"","isEnough":"0","qty":"1"}],"hele_supplier_id":"254","provinceId":"4524130","seller_id":"67927"}]
		String responseBody = "{\"code\":\"1\",\"msg\":\"\",\"result\":[{\"fee\":[{\"goodsId\":[\"17136\"],\"transfee\":[{\"feeType\":\"商家承担\",\"feeValue\":\"0.00\"}]}],\"hele_supplier_id\":\"44\",\"seller_id\":\"17556\"}]}";
		// HeleResponse respJson = JSONObject.parseObject(responseBody,
		// HeleResponse.class);
		HeleResponse heleResponse = new HeleResponse();
		String goodsId = "17136";
		List<String> goodsIdList = new ArrayList<>();
		List<Object> transportList = new ArrayList<>();
		goodsIdList.add(goodsId);
		Transport transport = new Transport();
		transport.setHele_supplier_id("44");
		transport.setSeller_id("17556");
		transport.getFee().get(0).setGoodsId(goodsIdList);
		transportList.add(transport);
		heleResponse.setResult(transportList);
		System.out.println(responseBody);
		System.out.println(JSON.toJSONString(heleResponse));

		// System.out.println(transport.getFee());

		// String responseBody =
		// "{\"code\":\"1\",\"msg\":\"\",\"result\":[{\"orderCode\":\"O2016010813520166117069\",\"heleOrderCode\":\"00_O2016010813520166117069_72_1601081400\"}]}";
		// String responseBody =
		// "{\"result\":{\"status\":\"1\",\"orderCode\":\"O2015111216433697084626\",\"heleOrderCode\":\"00_O2015111216433697084626_54_1511121650\",\"orderStatus\":\"40\"},\"code\":\"1\",\"msg\":\"收货成功。\",\"logic\":\"orderReceipt\"}";
		// JSONObject dataJson = JSONObject.parseObject(responseBody);
		// JSONObject responseBodyJson = JSONObject.parseObject(responseBody);
		//
		// JSONArray result = responseBodyJson.getJSONArray("result");
		// JSONArray fee = result.getJSONObject(0).getJSONArray("fee");
		// JSONArray transfeeArr =
		// fee.getJSONObject(0).getJSONArray("transfee");
		// JSONArray goodsid = fee.getJSONObject(0).getJSONArray("goodsId");
		// System.out.println(responseBodyJson);
		// System.out.println(result);
		// System.out.println(fee);
		// System.out.println(goodsid);
		// goodsid.clear();
		// goodsid.add("dafds");
		// result.getJSONObject(0).put("hele_supplier_id", "45");
		// result.getJSONObject(0).put("seller_id", "17557");

		// System.out.println(result);

	}
}
