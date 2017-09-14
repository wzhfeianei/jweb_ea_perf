package com.ea.model.hele.transport;


import java.util.Date;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")

public class TransfeeTest {	
	@Resource 
	private Date date;
	
	public void Test1(){
		System.out.println(111);
	}

}
