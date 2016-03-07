package com.yuanqingfei.order.backend.soap.order.domain;

import java.io.Serializable;

/**
 * 订单
 * 
 * @author Steven_Ge
 *
 */
// TODO: add attributes
public class BackendOrder implements Serializable {

	/**
	 * 订单编号（全局唯一）
	 */
	private String seqNo;

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7236952891308340241L;
	/**
	 * 订单状态
	 */
	private BackendOrderStatusEnum status;

	/**
	 * 获取订单状态
	 * 
	 * @return 当前状态
	 */
	public BackendOrderStatusEnum getStatus() {
		return status;
	}

	/**
	 * 设置订单状态
	 * 
	 * @param status
	 *            目标状态
	 */
	public void setStatus(BackendOrderStatusEnum status) {
		this.status = status;
	}

}
