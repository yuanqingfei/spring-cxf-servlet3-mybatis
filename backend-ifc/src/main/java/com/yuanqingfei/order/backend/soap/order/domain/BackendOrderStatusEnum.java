package com.yuanqingfei.order.backend.soap.order.domain;

/**
 * 订单状态
 * 
 * @author Steven_Ge
 *
 */
public enum BackendOrderStatusEnum {
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
