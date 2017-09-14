package com.ea.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ea.model.hele.HeleResponse;
import com.ea.model.hele.Order;
import com.ea.model.hele.OrderReceipt;
import com.ea.model.hele.Transport;
import com.ea.trade.thirdpart.hele.utils.MakeRSASign;
import com.ea.trade.util.HttpPostUtil;

@RestController
public class Hele {
	@Value("${hele.url}")
	private String url;
	@Value("${hele.publicKey}")
	private String publicKey;
	@Value("${hele.privateKey}")
	private String privateKey;

	private String hele_supplier_id = "";
	private String seller_id = "";
	private String goodsId = "";
	public static Logger log = Logger.getLogger("com.ea.controller.hele");
	private HeleResponse heleResponse = new HeleResponse();

	@RequestMapping(value = "/hele/api")
	@ResponseBody
	String api(HttpServletRequest request) {
		String body = RequestUnit.getRequestBody(request);
		String data = "";
		String logic = "/hele/";
		try {
			JSONObject dataJson = JSONObject.parseObject(body);
			data = dataJson.getString("data");
			logic += dataJson.getString("logic");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("请求内没有正确的转发路径");
		}

		try {
			// log.info("转发请求" + url + "/hele/" + logic + "\n");
			return HttpPostUtil.post(url + logic, MakeRSASign.decodeByPublicKey(data, publicKey));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "返回错误";
	}

	@RequestMapping(value = "/hele/transport", method = RequestMethod.POST)
	@ResponseBody
	String transport(HttpServletRequest request) {
		String body = RequestUnit.getRequestBody(request);
		// 请求格式=[{"areaId":"4524163","cityId":"4524157","goodsInfo":[{"goodsId":"42598","goods_spec_id":"","isEnough":"0","qty":"1"}],"hele_supplier_id":"254","provinceId":"4524130","seller_id":"67927"}]
		// 解析请求获取相应值
		String responseBody = "{\"code\":\"1\",\"msg\":\"\",\"result\":[{\"fee\":[{\"goodsId\":[\"17136\"],\"transfee\":[{\"feeType\":\"商家承担\",\"feeValue\":\"0.00\"}]}],\"hele_supplier_id\":\"44\",\"seller_id\":\"17556\"}]}";
		// 有个场景没有写，当结算商品为多个商品，只会取第一个商品，也就是现在功能只能结算一个商品

		JSONObject responseBodyJson = JSONObject.parseObject(responseBody);
		try {
			JSONArray transportJsonArr = (JSONArray) JSONArray.parse(body);
			hele_supplier_id = transportJsonArr.getJSONObject(0).getString("hele_supplier_id");
			seller_id = transportJsonArr.getJSONObject(0).getString("seller_id");
			JSONArray goodsInfo = transportJsonArr.getJSONObject(0).getJSONArray("goodsInfo");
			goodsId = goodsInfo.getJSONObject(0).getString("goodsId");

			// 修改响应
			List<String> goodsIdList = new ArrayList<>();
			List<Object> transportList = new ArrayList<>();
			goodsIdList.add(goodsId);
			Transport transport = new Transport();
			transport.setHele_supplier_id(hele_supplier_id);
			transport.setSeller_id(seller_id);
			transport.getFee().get(0).setGoodsId(goodsIdList);
			transportList.add(transport);
			heleResponse.setResult(transportList);

			// JSONArray result = responseBodyJson.getJSONArray("result");
			// JSONArray fee = result.getJSONObject(0).getJSONArray("fee");
			// JSONArray goodsIdArr =
			// fee.getJSONObject(0).getJSONArray("goodsId");
			// result.getJSONObject(0).put("hele_supplier_id",
			// hele_supplier_id);
			// result.getJSONObject(0).put("seller_id", seller_id);
			// goodsIdArr.clear();
			// goodsIdArr.add(goodsId);
			// log.info("响应体为:" + responseBodyJson.toJSONString());
			log.info("响应体为:" + JSON.toJSONString(heleResponse));
		} catch (Exception e) {
			log.info("解析请求失败,请求内没需要的参数");
			e.printStackTrace();
		}
		try {
			String key = MakeRSASign.dataEncodeByPublicKey(JSON.toJSONString(heleResponse), publicKey);
			// String key =
			// MakeRSASign.dataEncodeByPublicKey(responseBody,publicKey);
			log.info("和乐响应加密为:" + key);
			return key;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		responseBodyJson.put("msg", "加密失败");
		return responseBody;

	}

	@RequestMapping(value = "/hele/order", method = RequestMethod.POST)
	@ResponseBody
	String order(HttpServletRequest request) {
		// 请求Body：{"data":"DwZTSrDlahdetGgohklCEqQHDkGRD%2B9toCJKeYqSgckDh7NGYi8w9asRXPr8vttSwAlff4lyYHqYph2uZG%2BPWURcE7EkbQWSs%2BPfxSX8GYO%2FAUqumV3iYGR7l%2FPvE7IR6Omfo%2B533UDiGyq78hmd7E2dDPdMXRtKYRVPNul4o7wC%2FvGVnVSvcMtpgGTIR9JJPu%2F5UFVNHIq%2FvPj7pa2vO3jKpc1KVoTHmB92DbSKp35DXpqboDPUVhf%2BUBQDEWFJBlslroBySUlIW40yuszQo%2Bbw%2BUzLNwdi7ox1IeX5A6mx1bLNbg%2BtQzGI9PQLNgIKH33Xs5aoj1XtuIeDLeZFSzwGr9jU8c8lZzGqY4iSAJiVgaQsXYbbI3LWwllFRyvS1MQlwGyVotYk%2FggQ4eQvW%2BlgzmIQ68RBiZiYa8wZWC2UAhddKS7dx2TTZpnMNPLSNXGGQN5AKMmsKFcMDaF2PY36sgLVMOjmnjkoLcJRCTNA3WaTGdfzorgZ2BTRR8PkPIq81hQlR6n6QCDlaHyrkZftVumJNlPL4oCoPluj9OWjJ8z0U5H%2FGlXHmEDAL2zmI2ylS%2FNp0AoeDtXtPvyaESQV%2FlWDK%2Bst7Z77cioY1i8OzyyyH8syGBYtr3F8MySz58EEP8p91IHwp%2BLbMsTKO8mMkyx0YXbDxdwSRfnafDCKeHMWXDUHsHqOGcozJ9FyGbM2z%2FmrV2v3J6BoqxfPeVWdDXbOzKFtZf6fjlc9hoe7IB74cXu%2FUcxS4aMXbAMgBnszv7cREOKAAqsw4bJRJ7XU44uMAtAT1wg78brSuth52JwrwZ80nSk97iSCOV5pQuB88tGbQqk2m%2BSD1vATi05bYu5NOLYNqP7YZNu69xZ35slJDC0IVLdI0a8OnhVaM9e17L%2FD5cvDTdOufHLMi6VaHg6YFVH20FBVbc0ggNHMI%2BCi1brkDZRZvT0o1xYTXiuUePtrHPL3WZwBsmCIiM6Ug37huln95yvsxypdDnJZhn%2FfJpf4FuvA7C8rjZ%2FAKcsUhzAHWC7sR3NGAy8XBlPTYXjC9ocagrqTymU%2FQUxOXafY%2FVv5cUx5hL3dn20wqV34lV2ZACE%2BcdUlTk5yLuSnqdfkciCPRgf57OazADFWjUx%2B2rVsRUmF5PmokZ%2FMT2lN2HAb8iatbRzS5ArYMf8bSt5H3H90NoBzt5EXZrc%3D","encrypt":"RSA","logic":"order","rsaSign":"g%2FNC0LjUcvFalz1iyToc2qLzauy2gW2%2FRex4e1iCltXlkg8x60t50nolZQnbAOpAXgWeJ%2B%2BuFm8JhePwDKfkolZK8EML4U77PCB6ojPGDOqRxD%2FqTzmW06ZeLdWvAiRLJ4zZpCfiU5x%2BSXJJZXvjZyYrzzProfoMCPhRMG8ah58%3D","sys":"1"}
		// 请求Data：[{"deliveryTime":"","deliveryType":"0","goods":[{"goodsId":"14336","goodsMainPath":"http://img.365hele.com/upload/data/16510_1_.jpg_middle.jpg","goodsName":"花王妙而舒婴儿纸尿裤中号(M)64片","goodsPrice":"0.01","goodsQty":"1","skuId":"","skuName":""}],"goodsAmount":"0.01","invoiceTitle":"","invoiceType":"2","orderCode":"O2015111217024427429025","orderType":"ms","outOrderId":"1008290720201511121572032455","receiverArea":"东城区","receiverAreaCode":"4521986","receiverAreaInfo":"在一起吧","receiverMobile":"13528419073","receiverName":"你是我的","receiverTelephone":"","receiverZip":"","remark":"","shipPrice":"0.00","storeId":"54","storeName":"","totalAmount":"0.01","tradeNo":"2015111217024427451510","transport":"商家承担[0.00元]"}]
		// 响应Data：{"code":"1","msg":"","result":[{"orderCode":"O2015111217024427429025","heleOrderCode":"00_O2015111217024427429025_54_1511121755"}]}
		String body = RequestUnit.getRequestBody(request);

		try {
			JSONArray transportJsonArr = (JSONArray) JSONArray.parse(body);
			String orderCode = transportJsonArr.getJSONObject(0).getString("orderCode");
			Order order = new Order(orderCode);
			List<Object> list = new ArrayList<>();
			list.add(order);
			heleResponse.setResult(list);
			log.info("响应体为:" + JSON.toJSONString(heleResponse));
		} catch (Exception e) {
			log.info("解析请求失败,请求内没需要的参数");
			e.printStackTrace();
		}
		try {
			String key = MakeRSASign.dataEncodeByPublicKey(JSON.toJSONString(heleResponse), publicKey);
			log.info("和乐响应加密为:" + key);
			return key;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "加密失败";

	}

	@RequestMapping(value = "/hele/orderReceipt", method = RequestMethod.POST)
	@ResponseBody
	String orderReceipt(HttpServletRequest request) {
		// 请求Body：{"data":"tX6rTcSVGgm1Wm%2Be8ah87fP02%2FF1Q%2Btq6B%2B0GpdSoYLVFG9QoBwreePqxXPPg8IPggOJjCciVm5nSCBQQn2imNDiWjIn3Jbt8rqzQyfWc6zmsGcZYXYf865DQaabMNSCv3nlPN4lC%2F%2BVg808pgES7Su7HrMETXSbcOK980a2sINuMDlw9GwQLCkU%2FiNGjSAd4nHPOcxdIaRyU9syBnMxSwkkGqixDURFcFntKRQRe2cl3MDdzTQ2L%2BzwvQC2xgHyqdVaTw4GXqLeWK9h6fr1ATRvlrs5zEBg4YFOQkDfy27CnHPJJz98VNIPyuDJ%2F6PPFDX8OWC5GgjU2ITICPaC6A%3D%3D","encrypt":"RSA","logic":"orderReceipt","rsaSign":"l8O1%2BZcuQkAzMZ1QtZ35Q4D5v3vS0%2B1qRws%2BUhDqEpo7o%2Fe7VNllc%2FKwSacInhUozZuFy8OrxHGzZcN5Vahz6GQloSZRRoxfdSqjau2p8q0tTFVpKrLqFWvHz1D2TmOVC%2FL96GnpTBArh8nEK%2BF468qyxUTZaJ%2BdXl4bGJZYSDI%3D","sys":"1"}
		// "{\"data\":\"tX6rTcSVGgm1Wm%2Be8ah87fP02%2FF1Q%2Btq6B%2B0GpdSoYLVFG9QoBwreePqxXPPg8IPggOJjCciVm5nSCBQQn2imNDiWjIn3Jbt8rqzQyfWc6zmsGcZYXYf865DQaabMNSCv3nlPN4lC%2F%2BVg808pgES7Su7HrMETXSbcOK980a2sINuMDlw9GwQLCkU%2FiNGjSAd4nHPOcxdIaRyU9syBnMxSwkkGqixDURFcFntKRQRe2cl3MDdzTQ2L%2BzwvQC2xgHyqdVaTw4GXqLeWK9h6fr1ATRvlrs5zEBg4YFOQkDfy27CnHPJJz98VNIPyuDJ%2F6PPFDX8OWC5GgjU2ITICPaC6A%3D%3D\",\"encrypt\":\"RSA\",\"logic\":\"orderReceipt\",\"rsaSign\":\"l8O1%2BZcuQkAzMZ1QtZ35Q4D5v3vS0%2B1qRws%2BUhDqEpo7o%2Fe7VNllc%2FKwSacInhUozZuFy8OrxHGzZcN5Vahz6GQloSZRRoxfdSqjau2p8q0tTFVpKrLqFWvHz1D2TmOVC%2FL96GnpTBArh8nEK%2BF468qyxUTZaJ%2BdXl4bGJZYSDI%3D\",\"sys\":\"1\"}";
		// 请求Data：{"heleOrderCode":"00_O2015111216221244547149_54_1511129999","orderCode":"O2015111216221244549999","receiverName":"你是我的","receiverTime":"2015-11-12
		// 17:51:49"}
		// "{\"heleOrderCode\":\"00_O2015111216221244547149_54_1511129999\",\"orderCode\":\"O2015111216221244549999\",\"receiverName\":\"你是我的\",\"receiverTime\":\"2015-11-12
		// 17:51:49\"}";
		// 响应Data：{"result":{\"status\":\"1\",\"orderCode\":\"O2015111216221244547149\",\"heleOrderCode\":\"00_O2015111216221244547149_54_1511121655\",\"orderStatus\":\"40\"},"code":"1","msg":"收货成功。","logic":"orderReceipt"}
		String body = RequestUnit.getRequestBody(request);
		try {
			JSONObject jso = JSON.parseObject(body);
			String orderCode = jso.getString("orderCode");
			String heleOrderCode = jso.getString("heleOrderCode");
			System.out.println(orderCode);
			OrderReceipt orderReceipt = new OrderReceipt(orderCode);
			orderReceipt.setHeleOrderCode(heleOrderCode);
			List<Object> list = new ArrayList<>();
			list.add(orderReceipt);
			heleResponse.setResult(list);
			log.info("响应体为:" + JSON.toJSONString(heleResponse));
		} catch (Exception e) {
			log.info("解析请求失败,请求内没需要的参数");
			e.printStackTrace();
		}
		try {
			String key = MakeRSASign.dataEncodeByPublicKey(JSON.toJSONString(heleResponse), publicKey);
			log.info("和乐响应加密为:" + key);
			return key;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "加密失败";

	}

	// orderRefundApply

	@RequestMapping(value = "/hele/orderRefundApply", method = RequestMethod.POST)
	@ResponseBody
	String orderRefundApply(HttpServletRequest request) {
		String body = RequestUnit.getRequestBody(request);
		return "没有请求样例" + body;

	}

}
