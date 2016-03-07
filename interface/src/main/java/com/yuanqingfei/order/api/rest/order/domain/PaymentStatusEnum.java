package com.yuanqingfei.order.api.rest.order.domain;

/**
 * 支付状态
 * 
 * @author Steven_Ge
 *
 */
public enum PaymentStatusEnum {
	/**
	 * 1已支付
	 */
	COMPLETED(1),
	/**
	 * 2未支付
	 */
	OPEN(2);
	private long code;

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	private PaymentStatusEnum(long code) {
		this.code = code;
	}
}
