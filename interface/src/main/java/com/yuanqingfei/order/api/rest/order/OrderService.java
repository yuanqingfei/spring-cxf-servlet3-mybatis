package com.yuanqingfei.order.api.rest.order;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.yuanqingfei.order.api.rest.common.ApiException;
import com.yuanqingfei.order.api.rest.order.domain.Order;
import com.yuanqingfei.order.api.rest.order.domain.OrderStatusEnum;


public interface OrderService {

	final static public String DEFAULT_OFFSET = "0";
	final static public String DEFAULT_SIZE = "-1";

	/**
	 * 
	 * @param offset
	 *            初始位置,默认为0
	 * @param size
	 *            长度，默认为-1，表示全部取出
	 * @param timestamp
	 *            校验参数，输入当前时间
	 * @param apiKey
	 *            校验标识位
	 * @param signature
	 *            签名，请参阅本文档起始位置关于异常的说明
	 * @return 所有未完成处理的订单列表
	 * @throws ApiException
	 *             异常错误，请参阅本文档起始位置关于异常的说明
	 */
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public List<Order> list(
			@QueryParam(value = "offset") @DefaultValue(DEFAULT_OFFSET) int offset,
			@QueryParam(value = "size") @DefaultValue(DEFAULT_SIZE) int size,
			@QueryParam(value = "timestamp") Date timestamp,
			@QueryParam(value = "apiKey") String apiKey,
			@QueryParam(value = "signature") String signature)
			throws ApiException;

	/**
	 * 单独更新订单状态接口
	 * 
	 * @param orderSeq
	 *            订单编号
	 * @param status
	 *            目标状态
	 * @param timestamp
	 *            时间戳， 校验参数，输入当前时间
	 * @param apiKey
	 *            校验标识位
	 * @param signature
	 *            签名，请参阅本文档起始位置关于异常的说明
	 * @throws ApiException
	 *             异常错误，请参阅本文档起始位置关于异常的说明
	 */
	@Path("{orderSeq}")
	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public void update(@PathParam("orderSeq") String orderSeq,
			OrderStatusEnum status,
			@QueryParam(value = "timestamp") Date timestamp,
			@QueryParam(value = "apiKey") String apiKey,
			@QueryParam(value = "signature") String signature)
			throws ApiException;

	/**
	 * 批量更新订单状态接口
	 * 
	 * @param orderSeqList
	 *            订单编号列表
	 * @param status
	 *            目标状态
	 * @param timestamp
	 *            时间戳， 校验参数，输入当前时间
	 * @param apiKey
	 *            校验标识位
	 * @param signature
	 *            签名，=MD5(所有的参数+MD5(secretKey))构成，请参阅文档说明
	 * @throws ApiException
	 *             异常错误，请参阅文档说明
	 */
	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public void update(List<String> orderSeqList, OrderStatusEnum status,
			@QueryParam(value = "timestamp") long timestamp,
			@QueryParam(value = "apiKey") String apiKey,
			@QueryParam(value = "signature") String signature)
			throws ApiException;
}
