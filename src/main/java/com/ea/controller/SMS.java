package com.ea.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ea.redis.RedisComponent;
import com.ea.sms.SendSmsResp;

@RestController
public class SMS {
	@Autowired
	private RedisComponent redisComponet;

	@RequestMapping("/")
	String home() {
		return "Hello World!";
	}

	@RequestMapping(value = "/sms/send", method = RequestMethod.POST)
	@ResponseBody
	SendSmsResp res(HttpServletRequest request) {
		String mobile = request.getParameter("mobile");
		SendSmsResp s = new SendSmsResp();
		redisComponet.updataVerifyCode(mobile);
		s.setCode("1");
		s.setMsg("短信验证码修改成功，随机验证码被强制修改为:0000000");
		s.setT_id("t_id000000");
		s.settId("tId");
		s.setState((long) 0.1415);

		return s;

	}



}
