package com.yuanqingfei.order.api.rest.order.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 * 
 * @author Steven_Ge
 *
 */
public class Order implements Serializable {

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
	private OrderStatusEnum status;

	/**
	 * 获取订单状态
	 * 
	 * @return 当前状态
	 */
	public OrderStatusEnum getStatus() {
		return status;
	}

	/**
	 * 设置订单状态
	 * 
	 * @param status
	 *            目标状态
	 */
	public void setStatus(OrderStatusEnum status) {
		this.status = status;
	}

	/**
	 * 订单金额
	 */
	private double amount;
	/**
	 * 支付方式
	 */
	private PaymentMethodEnum paymentMethod;
	/**
	 * 支付状态
	 */
	private PaymentStatusEnum paymentStatus;
	/**
	 * 应收金额
	 */
	private double receivable;
	/**
	 * 取货地址
	 */
	private String rAddress;
	/**
	 * 收货地址
	 */
	private String dAddress;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public PaymentMethodEnum getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethodEnum paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentStatusEnum getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatusEnum paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public double getReceivable() {
		return receivable;
	}

	public void setReceivable(double receivable) {
		this.receivable = receivable;
	}

	public String getrAddress() {
		return rAddress;
	}

	public void setrAddress(String rAddress) {
		this.rAddress = rAddress;
	}

	public String getdAddress() {
		return dAddress;
	}

	public void setdAddress(String dAddress) {
		this.dAddress = dAddress;
	}

	/**
	 * 原始json对象的payload，以JSON字符串方式提供
	 */
	private String payload;

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	/**
	 * 订单时间(保留)
	 */
	private Date orderDate;

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

}
