package com.yuanqingfei.order.api.rest.order.domain;

/**
 * 订单状态
 * 
 * @author Steven_Ge
 *
 */
public enum OrderStatusEnum {
	/**
	 * 可用
	 */
	READY,
	/**
	 * 处理中
	 */
	IN_PROGRESS,
	/**
	 * 已用
	 */
	COMPLETED
}
