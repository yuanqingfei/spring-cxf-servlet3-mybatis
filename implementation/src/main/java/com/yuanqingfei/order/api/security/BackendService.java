package com.yuanqingfei.order.api.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

import com.yuanqingfei.order.backend.soap.order.BackendOrder;
import com.yuanqingfei.order.backend.soap.order.BackendOrderException_Exception;
import com.yuanqingfei.order.backend.soap.order.BackendOrderService;


public class BackendService {
	public void callService(){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(BackendOrderService.class);
		factory.setAddress("http://localhost:8080/backend-impl/ws/BackendOrderServiceSC");
		factory.getInInterceptors().add(new org.apache.cxf.interceptor.LoggingInInterceptor());

		//WS-Security输出拦截器
		Map<String, Object> outProps = new HashMap<String, Object>();
		outProps.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
		//添加用户名
		outProps.put(WSHandlerConstants.USER, "admin");
		outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
		outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordCallbackHandler.class.getName());

		factory.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
		
		factory.getOutInterceptors().add(new org.apache.cxf.interceptor.LoggingOutInterceptor());

		BackendOrderService backendWS = factory.create(BackendOrderService.class);
		try {
			List<BackendOrder> list = backendWS.listOrders(1, 1);
			System.out.println(list.size());
		} catch (BackendOrderException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
