package com.yuanqingfei.order.backend;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.yuanqingfei.order.backend.ServiceConfig;
import com.yuanqingfei.order.backend.mq.MessageSender;
import com.yuanqingfei.order.backend.mq.OrderCollector;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = ServiceConfig.class) 
public class OrderCollectorTest {
	
	@Autowired
	private OrderCollector receiver;
	
	@Autowired
	private MessageSender sender;
	
	@Before
	public void setUp(){
		Assert.notNull(sender);
		sender.send("Hello, SF!");
	}

	@Test
	public void testReceive() throws InterruptedException{
		Assert.notNull(receiver);
		Thread.sleep(3000);
	}
	
}
