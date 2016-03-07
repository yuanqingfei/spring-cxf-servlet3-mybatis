package com.yuanqingfei.order.backend;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.yuanqingfei.order.backend.mybatis.OrderMapper;
import com.yuanqingfei.order.backend.soap.order.domain.BackendOrder;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = ServiceConfig.class) 
public class OrderMapperTest {
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Test
	public void testGetOrder(){
		
		Assert.notNull(orderMapper);
		BackendOrder order = orderMapper.getOrder("111");
		Assert.notNull(order);
		
	}

}
