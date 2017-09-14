package com.ea.redis;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.ea.sms.BaseSmsPo;

@Component
public class RedisComponent {

	@Autowired
	// 操作字符串的template，StringRedisTemplate是RedisTemplate的一个子集
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	// RedisTemplate，可以进行所有的操作
	private RedisTemplate<String, String> redisTemplate;

	public void updataVerifyCode(String phone) {
		Thread thread = new RedisThread(redisTemplate, phone);
		thread.start();
	}

	public void set(String key, String value) {
		ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
		boolean bExistent = this.stringRedisTemplate.hasKey(key);
		if (bExistent) {
			System.out.println("this key is bExistent!");
		} else {
			ops.set(key, value);
		}
	}

	public <T> Map<Object, Object> getCacheMap(String key) {
		Map<Object, Object> map = this.redisTemplate.opsForHash().entries(key);
		map.put("dafsd", "dafsdasdf");
		return map;
	}

	public String get(String key) {
		return this.stringRedisTemplate.opsForValue().get(key);
	}

	public void del(String key) {
		this.stringRedisTemplate.delete(key);
	}

	public void sentinelSet(BaseSmsPo sms) {
		String key = null;
		try {
			key = new String(sms.getCode().getBytes("gbk"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(key);
		redisTemplate.opsForValue().set(key, sms.toString());
	}

	public void getAllKeys() {
		System.out.println("All KEYS:");
		System.out.println(this.redisTemplate.keys("*"));

	}
}
