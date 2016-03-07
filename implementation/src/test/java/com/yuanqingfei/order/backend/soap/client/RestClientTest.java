package com.yuanqingfei.order.backend.soap.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yuanqingfei.order.api.CxfConfig;
import com.yuanqingfei.order.api.rest.order.domain.Order;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(classes = CxfConfig.class) 
public class RestClientTest {
	

	@Test
	public void testCall(){
		List<Object> providers = new ArrayList<Object>();
		providers.add( new JacksonJsonProvider() );
		WebClient client = WebClient.create("http://localhost:8080/order-impl/rest/order", providers);
		client.path("/");
		client.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).acceptEncoding("UTF-8");
		
		Collection<? extends Order> orders = client.getCollection(Order.class);
		Assert.assertNotNull(orders);
		Assert.assertTrue(orders.size() == 0);
	}

}
