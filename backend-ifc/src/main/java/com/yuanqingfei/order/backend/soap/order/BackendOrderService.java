package com.yuanqingfei.order.backend.soap.order;

import java.util.List;

import javax.jws.WebService;

import com.yuanqingfei.order.backend.soap.order.domain.BackendOrder;
import com.yuanqingfei.order.backend.soap.order.domain.BackendOrderStatusEnum;

/**
 * 
 */
@WebService
public interface BackendOrderService {

	final static public String DEFAULT_OFFSET = "0";
	final static public String DEFAULT_SIZE = "-1";

	/**
	 * 
	 * @param offset
	 *            初始位置,默认为0
	 * @param size
	 *            长度，默认为-1，表示全部取出
	 * @return 所有未完成处理的订单列表
	 * @throws BackendOrderException
	 *             异常错误
	 */
	List<BackendOrder> listOrders(int offset, int size)
			throws BackendOrderException;

	/**
	 * 单独更新订单状态接口
	 * 
	 * @param orderSeq
	 *            订单编号
	 * @param status
	 *            目标状态
	 * @throws BackendOrderException
	 *             异常错误
	 */
	void updateOrderStatus(String orderSeq, BackendOrderStatusEnum status)
			throws BackendOrderException;

	/**
	 * 批量更新订单状态接口
	 * 
	 * @param orderSeqList
	 *            订单编号列表
	 * @param status
	 *            目标状态
	 * @throws BackendOrderException
	 *             异常错误
	 */
	void updateOrderStatusList(List<String> orderSeqList,
			BackendOrderStatusEnum status) throws BackendOrderException;
}
