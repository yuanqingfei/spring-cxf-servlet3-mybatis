package com.yuanqingfei.order.backend.soap.client;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuanqingfei.order.api.CxfConfig;
import com.yuanqingfei.order.backend.soap.order.BackendOrderException_Exception;
import com.yuanqingfei.order.backend.soap.order.BackendOrderService;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = CxfConfig.class) 
public class SoapClientTest {
	
	@Autowired
	BackendOrderService service;
	
	@Test
	public void testCall() throws BackendOrderException_Exception{
		Assert.assertNotNull(service);
		List list = service.listOrders(0, 1);
		Assert.assertNotNull(list);
		Assert.assertTrue(list.size() == 0);
	}

}
