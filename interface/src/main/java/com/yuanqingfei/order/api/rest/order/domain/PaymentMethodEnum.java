package com.yuanqingfei.order.api.rest.order.domain;

/**
 * 订单支付方式
 * 
 */
public enum PaymentMethodEnum {
	/**
	 * 1在线支付
	 */
	ONLINE(1),
	/**
	 * 2代收货款
	 */
	CASH(2);
	private long code;

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	private PaymentMethodEnum(long code) {
		this.code = code;
	}
}
