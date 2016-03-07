package com.yuanqingfei.order.api.rest;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuanqingfei.order.api.rest.common.ApiException;
import com.yuanqingfei.order.api.rest.order.OrderService;
import com.yuanqingfei.order.api.rest.order.domain.Order;
import com.yuanqingfei.order.api.rest.order.domain.OrderStatusEnum;
import com.yuanqingfei.order.backend.soap.order.BackendOrder;
import com.yuanqingfei.order.backend.soap.order.BackendOrderException_Exception;
import com.yuanqingfei.order.backend.soap.order.BackendOrderService;

public class OrderServiceImpl implements OrderService {
	
	final private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private WebServiceContext context;

	@Autowired
	public BackendOrderService backendOrderService;
	
	@Override
	public List<Order> list(int offset, int size, Date timestamp,
			String apiKey, String signature) throws ApiException {
		List<Order> list = new ArrayList<>();
		try {
			List<BackendOrder> backOrders = backendOrderService.listOrders(offset, size);
			for(BackendOrder bOrder : backOrders){
				list.add(new Order());
			}
			log.info("call rest");
			System.out.println("call rest");
		} catch (BackendOrderException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void update(String orderSeq, OrderStatusEnum status, Date timestamp,
			String apiKey, String signature) throws ApiException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(List<String> orderSeqList, OrderStatusEnum status,
			long timestamp, String apiKey, String signature)
			throws ApiException {
		// TODO Auto-generated method stub
		
	}



}
