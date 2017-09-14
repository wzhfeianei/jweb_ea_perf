package com.ea.perf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ea.redis.RedisComponent;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerfApplicationTests {
	@Autowired
	private RedisComponent redisComponet;

	@Test
	public void get() {
		redisComponet.getAllKeys();
		redisComponet.updataVerifyCode("18000000006");
		// Map<String,String> scmapredisComponet.get("cp:pcode:18000000004");
	}
	

}
