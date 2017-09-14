package com.ea.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

public class RequestUnit {
	static Logger log = Hele.log;
	public static String getRequestBody(HttpServletRequest request) {
		log.info("接收到请求，来自:" + request.getRemoteAddr() + ":" + request.getRemotePort());

		BufferedReader br;
		String str, body = "";
		try {
			br = request.getReader();
			while ((str = br.readLine()) != null) {
				body += str;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		log.info("请求内容为:" + body);
		return body;

	}
}
