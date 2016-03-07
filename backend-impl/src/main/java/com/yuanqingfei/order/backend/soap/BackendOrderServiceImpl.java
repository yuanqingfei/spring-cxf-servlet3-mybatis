
package com.yuanqingfei.order.backend.soap;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import org.apache.log4j.Logger;

import com.yuanqingfei.order.backend.soap.order.BackendOrderException;
import com.yuanqingfei.order.backend.soap.order.BackendOrderService;
import com.yuanqingfei.order.backend.soap.order.domain.BackendOrder;
import com.yuanqingfei.order.backend.soap.order.domain.BackendOrderStatusEnum;

/**
 * 
 */
public class BackendOrderServiceImpl implements BackendOrderService {
	final private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private WebServiceContext context;

	@Override
	public List<BackendOrder> listOrders(int offset, int size)
			throws BackendOrderException {
		log.debug("listOrders");
		// TODO Auto-generated method stub
		return Collections.EMPTY_LIST;
	}

	@Override
	public void updateOrderStatus(String orderSeq, BackendOrderStatusEnum status)
			throws BackendOrderException {
		log.debug("updateOrderStatus" + orderSeq + status);
		// TODO Auto-generated method stub

	}

	@Override
	public void updateOrderStatusList(List<String> orderSeqList,
			BackendOrderStatusEnum status) throws BackendOrderException {
		log.debug("updateOrderStatusList" + orderSeqList + status);
		// TODO Auto-generated method stub

	}

}
