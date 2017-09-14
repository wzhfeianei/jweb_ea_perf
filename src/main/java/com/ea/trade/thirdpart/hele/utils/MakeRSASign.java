package com.ea.trade.thirdpart.hele.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 和乐网签名工具类
 * 
 * @author livi
 *
 */
public class MakeRSASign {

	// 公钥
	public final static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDmw+EVpNffxQbkJBFuWOGGQAwRUzvSNRFv6DEXieyf+peFLKLu5Xs05+LaePz7ye4XMUwDHmgTabTyHqMb6v4RcdkQcH0QbYgpi4mT3m7HNnJYBoOcl8pueO+gMhLpTpDNp1vdWdumjMBHZmYXIhggg/yJXEK7ENdqpJOH0gV/eQIDAQAB";
	
	// 私钥
	public final static String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAObD4RWk19/FBuQkEW5Y4YZADBFTO9I1EW/oMReJ7J/6l4Usou7lezTn4tp4/PvJ7hcxTAMeaBNptPIeoxvq/hFx2RBwfRBtiCmLiZPebsc2clgGg5yXym5476AyEulOkM2nW91Z26aMwEdmZhciGCCD/IlcQrsQ12qkk4fSBX95AgMBAAECgYEAqdejU1XHGMgfoUw2lBvQqQl8mEfFcJpkYhPoBeYahUhfusG2jdPSkYbV/Wws7niZxvIeevEnMpqLdeQ8snCLWw9lmG/ZVt8yuaohg3isvJ053ZSR4bZQ90BPSne7ERqYZVc//L9MhMBbYPbL66gToOSaWmJt9WgevXJgHpl0Ds0CQQD+R7371Je4QmGYICZVNiJA4xyF2/otsMyegc6a+6bAvTlP/auzy/AGpAnwCikTWam/QcOsK44MEA1UCzce1P77AkEA6FNsYTaREOPjQgX5vpyybfGNVu5paDvQWXZ7Qr+6C6PNk963e4r0ZcmxdBqlqPlcGQpzlvFG5ZpFL89GThzhGwJAIUg+o7Gl0iZOI91BZzHYtemEGDk8u7JCdhTDeCGsJ9ocoE2xpazx08ywyExsJ+46e6Ad/nEM/KM1O2TkaBK09QJBAMJAxpT9+AuEUAdrUae0A2j0GHbAP3g80veKnoNwJdIHsyNZjFcLF1BodSyb/zmqzfvOBloGAX6HICRajfFQKlMCQQCGwBLv1pnA5Kb6sSz2fHlFk+/ibqIDfLPSwAZgub1DJh3d2vpPA+1M0L1tOfH0NgVV3tkjJShhVBKXZJgP4NBF";

	/**
	 * 对发送的data进行签名
	 * 
	 * @param source
	 * @param privateKey
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String dataSign(String source, String privateKey) throws UnsupportedEncodingException {

		byte[] data = source.getBytes();
		byte[] encodedData = RSAPrivate.encryptByPrivateKey(data, RSAPrivate.getPrivateKey(privateKey));
		String s1 = URLEncoder.encode(Base64.encode(encodedData), "UTF-8");

		return s1;

	}

	/**
	 * 对私钥进行加密
	 * 
	 * @param source
	 * @param privateKey
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> getSign(String source, String privateKey) throws UnsupportedEncodingException {

		Map<String, String> map = new HashMap<String, String>();
		System.out.println(source);

		// 第三方系统使用私钥加密原文
		byte[] data = source.getBytes();
		byte[] encodedData = RSAPrivate.encryptByPrivateKey(data, RSAPrivate.getPrivateKey(privateKey));
		String encryptSource = URLEncoder.encode(Base64.encode(encodedData), "UTF-8");
		map.put("data", encryptSource);
		// 第三方系统使用私钥签名签名
		String sign = RSAPrivate.sign(Base64.decode(URLDecoder.decode(encryptSource, "UTF-8")),
				RSAPrivate.getPrivateKey(privateKey));
		sign = URLEncoder.encode(sign, "UTF-8");
		map.put("rsaSign", sign);

		return map;
	}
	
	/**
	 * 和乐网发送数据公钥加密
	 * 
	 * @param source
	 * @param PublicKey
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String dataEncodeByPublicKey(String source, String PublicKey) throws UnsupportedEncodingException {

		byte[] data = source.getBytes();
		byte[] encodedData = RSAPrivate.encryptByPublicKey(data, RSAPrivate.getPublicKey(PublicKey));
		String s1 = URLEncoder.encode(Base64.encode(encodedData), "UTF-8");

		return s1;

	}
	
	

	/**
	 * 
	 * 对和乐网返回数据解密
	 * 
	 * @param source
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String source, String key) throws UnsupportedEncodingException {
		byte[] decodedData = RSAPrivate.decryptByPrivateKey(Base64.decode(URLDecoder.decode(source, "UTF-8")),
				RSAPrivate.getPrivateKey(key));
		return new String(decodedData);
	}
	
	
	
	/**
	 * 
	 * 对和乐网返回数据解密
	 * 
	 * @param source
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	
	public static String decodeByPublicKey(String source, String PublicKey) throws UnsupportedEncodingException {
		byte[] decodedData = RSAPrivate.decryptByPublicKey(Base64.decode(URLDecoder.decode(source, "UTF-8")),
				RSAPrivate.getPublicKey(PublicKey));
		return new String(decodedData);
	}
	
	

	

	public static void main(String[] args) {
		//String pub ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDmw+EVpNffxQbkJBFuWOGGQAwRUzvSNRFv6DEXieyf+peFLKLu5Xs05+LaePz7ye4XMUwDHmgTabTyHqMb6v4RcdkQcH0QbYgpi4mT3m7HNnJYBoOcl8pueO+gMhLpTpDNp1vdWdumjMBHZmYXIhggg/yJXEK7ENdqpJOH0gV/eQIDAQAB";
		//String str = "G7iMM1WRa3UYPqrFCPneVEhbJJBvkhcjFFNVGzR3U%2B6ebAvxsIgOCHe6Bui%2FvpLqpnUMfBEXiiqF6Era5hz5FW%2BWS499i0JQdt66AIyrjhHkxasDxb91AvZmds3%2FmDWi70%2Fjch75yCu4vOTlNAMzUHRSvCEhWjhXZHknNAIO6nYhxT9sIkwOudChihblMSuF8BNp17q%2Fjrp%2F0w681NH%2FV0bDyiZoJvNHBXudVfwZy%2Fde%2Be4GaB34cIgb44fTUyH9dQ82AyG%2BpAfxHuXITz74bbgIrAoSdU1DqKtq4RiOsUWzpRGAeeOkuyg5E0ovIkiLBCyi0CYJc32AB3sFFE%2BUmg%3D%3D";
		//String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKLs9y42MGZYnW2jPz7aci/LeKXgBrlG9AD1HKy5Uaf71H5YruSKHGw2ZWrhQ/Ft1Y3Fgy78WS1zJqGnMFKTE8t1VjffhwkRCfulfiv2ewAlWYxFYy38Idmn1hWy6pq5fn5TkDja4spwycBKTQW/hvheWZWY8e+50x6FILv2rzkJAgMBAAECgYBrlGDayLlIuzaoUGTQkdlYKdhuXvDRokI356GZg3yhmS2Eh/ZjvbZq1bg9zKTaZBXg2Qx4F/SiiCh4ETddLvSgEQNw5eRVuwgFu+C6boyN9cA2hbB/FQgZZ8u/UBha57aLeERGVGCxYbZDe7ADlcqgb5aOSPTknnNq2+OFf3xoKQJBAO1pgiECgXGSYaav7wXoTGOxFnXDgrUHiyzLtS7BV7vTC/rpvChKNgYkc4Bdx2UeLuktBJ3rtbdfszVFEFqaRmMCQQCvroMGseTxP6I1AN0Sm1ADsGjPBq6d9pikQUTd5NVuFbkJA3G/43+vbL/vH8r5Aq3UZpDFPigx8U+zfVc7knijAkAmmTPF6CFhdJoZvprOzBC3WJA9Pzcd9YcEFm5zlo2MBOj6t0uXBVG5N+3rXdPQNdMMp/wLLMpTcOSy8XKSOGjfAkA8c4lEiXwlA2d2Y5iuqWDMFCzMaBk34728b3UaAX7rhXAJSPUgOVEbdQlKaB84thJIjw5rwKqmHMn3Ej8tEYaPAkBQp4IeZ4qFGLb2s/mSWEbVZym9xMpWiBmsZqXVdzDI8Vs6HfdVx02bLslXGHwMOLCdOalRwPv7sy2F8uc+j/mT";
		//String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKLs9y42MGZYnW2jPz7aci/LeKXgBrlG9AD1HKy5Uaf71H5YruSKHGw2ZWrhQ/Ft1Y3Fgy78WS1zJqGnMFKTE8t1VjffhwkRCfulfiv2ewAlWYxFYy38Idmn1hWy6pq5fn5TkDja4spwycBKTQW/hvheWZWY8e+50x6FILv2rzkJAgMBAAECgYBrlGDayLlIuzaoUGTQkdlYKdhuXvDRokI356GZg3yhmS2Eh/ZjvbZq1bg9zKTaZBXg2Qx4F/SiiCh4ETddLvSgEQNw5eRVuwgFu+C6boyN9cA2hbB/FQgZZ8u/UBha57aLeERGVGCxYbZDe7ADlcqgb5aOSPTknnNq2+OFf3xoKQJBAO1pgiECgXGSYaav7wXoTGOxFnXDgrUHiyzLtS7BV7vTC/rpvChKNgYkc4Bdx2UeLuktBJ3rtbdfszVFEFqaRmMCQQCvroMGseTxP6I1AN0Sm1ADsGjPBq6d9pikQUTd5NVuFbkJA3G/43+vbL/vH8r5Aq3UZpDFPigx8U+zfVc7knijAkAmmTPF6CFhdJoZvprOzBC3WJA9Pzcd9YcEFm5zlo2MBOj6t0uXBVG5N+3rXdPQNdMMp/wLLMpTcOSy8XKSOGjfAkA8c4lEiXwlA2d2Y5iuqWDMFCzMaBk34728b3UaAX7rhXAJSPUgOVEbdQlKaB84thJIjw5rwKqmHMn3Ej8tEYaPAkBQp4IeZ4qFGLb2s/mSWEbVZym9xMpWiBmsZqXVdzDI8Vs6HfdVx02bLslXGHwMOLCdOalRwPv7sy2F8uc+j/mT";	
		String sourceJson;
		try {
			InputStreamReader stdin = new InputStreamReader(System.in);// 键盘输入
			BufferedReader bufin = new BufferedReader(stdin);
			System.out.print("请输入字符：   ");
			sourceJson = bufin.readLine();
			Map<String, String> sign = getSign(sourceJson, PRIVATE_KEY);
			System.out.println("data=" + sign.get("data").toString());
			System.out.println("rsaSign=" + sign.get("rsaSign").toString());
			System.out.println("decodedata="+decodeByPublicKey(sign.get("rsaSign").toString(),PUBLIC_KEY));
			//DES des = new DES();
			//System.out.println("decodedata="+des.decrypt(sign.get("data").toString()));
			//String res = decode(str, prvKey);
			//System.out.println(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * String password; boolean redo=false;
		 * 
		 * do{
		 * 
		 * try { InputStreamReader stdin = new
		 * InputStreamReader(System.in);//键盘输入 BufferedReader bufin = new
		 * BufferedReader(stdin); System.out.print ("请输入字符：   "); password =
		 * bufin.readLine(); DES des = new DES(); // password=
		 * des.encrypt(password); // System.out.println("加密后:"+password);
		 * password= des.decrypt(password); System.out.println("解密后:"+password);
		 * System.out.print ("需要继续吗?(Y/N)：   "); stdin = new
		 * InputStreamReader(System.in);//键盘输入 bufin = new
		 * BufferedReader(stdin); String flag = bufin.readLine().toUpperCase();
		 * redo="Y".equals(flag)?true:false;
		 * 
		 * } catch (Exception ex) { ex.printStackTrace(); }
		 * 
		 * } while(redo);
		 */
	}
}
