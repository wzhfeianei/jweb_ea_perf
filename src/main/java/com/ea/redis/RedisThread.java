package com.ea.redis;

import java.util.Set;
import java.util.logging.Logger;

import org.springframework.data.redis.core.RedisTemplate;

public class RedisThread extends Thread {
	private RedisTemplate<String, String> redisTemplate;
	private String phone;
	private Logger log = Logger.getLogger("com.ea.controller.sms");

	public RedisThread(RedisTemplate<String, String> redisTemplate, String phone) {
		this.redisTemplate = redisTemplate;
		this.phone = phone;

	}

	public void run() {
		String codekey = null;
		String code = null;

		int i = 0;
		while (null == codekey) {
			Set<String> keySet = this.redisTemplate.keys("*" + phone + "*");
			for (String s : keySet) {
				if (s.contains("code")) {
					log.info("查询到符合条件的codekey:" + s);
					codekey = s;
				}
			}

			try {
				// 先获取到较验码的KEY
				Set<Object> codeSet = this.redisTemplate.opsForHash().keys(codekey);
				for (Object s : codeSet) {
					code = (String) this.redisTemplate.opsForHash().get(codekey, s);
					log.info("key:" + code);
					if (code.contains("code")) {
						log.info("查询到验证码存放地址:" + code);
						log.info(phone + "号码修改前手机的较验码/的code为:"
								+ (String) this.redisTemplate.opsForHash().get(code, "code"));
						this.redisTemplate.opsForHash().put(code, "code", "000000");
						log.info("修改后手机的较验码的code为:" + (String) this.redisTemplate.opsForHash().get(code, "code"));
						break;
					}

				}
				// 大并发下遍历性能待考察，如果不好则指定删除具体的KEY
				keySet = this.redisTemplate.keys("*" + phone + "*");
				for (String s : keySet) {
					if (s.contains("lock")) {
						log.info("删除时间内限制发短信次数:" + s);
						this.redisTemplate.delete(s);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				log.info("手机" + phone + "的验证码还没有写入redis，等待写入...");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			i++;
			if (i > 99) {
				log.info("在100次查询内没有查到相关手机信息，强制退出");
				break;
			}
		}
	}

}
