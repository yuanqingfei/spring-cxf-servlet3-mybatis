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

/**
 * <h1>订单服务</h1> <br>
 * 本服务使用简化版HMAC（Hash-based Message Authentication
 * Code）的方式，使用MD5的方式对所有参数进行单向混淆生成签名防止篡改，同时使用双方约定的appKey和appSecret进行客户端认证。<br>
 * <h2>
 * signature验签码字符串生成规则</h2> <br>
 * 1. 范围：接口的所有入参（除signature）和MD5(apiSecret)(为每个调用系统事前指定)。<br>
 * 序列化方式：将需要验签的参数按其参数名称升序排列（按参数名称首字母降序升序排列，如首字母相同则按下一位字母降序排列，以此类推），
 * 然后将排序好的参数的值用‘_’( 下划线) 连接起来，组成最终需要验签的字符串。<br>
 * 2.1.
 * 传入参数为对象时，此参数的值使用{}表示，对象中如有多个属性，则将属性名称按字母升序排列，每个属性字符串（属性字符串是由属性名和属性值使用‘=’(等号)
 * 连接组成）之间使用‘_’连接。例如: {name=tom_age=23} 如对象中的某个属性为空，则生成的字符串中将忽略此属性。
 * 如一个对象中的所有属性均为空，则此对象生成的字符串为{}。<br>
 * 2.2. 传入参数为数组时，此参数的值使用[]表示，数组中的各个项之间使用‘, ’（逗号+一个空格）连接得到拼接的字符串。<br>
 * 2.3.
 * Date类型使用Date.getTime()得到拼接的字符串，其他类型（Integer，Double等）使用toString()得到拼接的字符串。<br>
 * 3. signature = MD5(序列化(所有除signature外的入参,MD5(apiSecret)))<br>
 * <i>*使用32位大写MD5</i>
 * 
 * <h2>HTTP code说明</h2><br>
 * 服务器端只需要简单的将错误信息封装到WebApplicationException中抛出；正常情况下，客户端能够获得相应的预定义的返回对象。一旦异常发生，
 * 客户端获得的是在WebApplicationException中封装的status code和error message。 <br>
 * 
 * <table border=1 summary="http code summary">
 * <tr>
 * <th>code</th>
 * <th>desc</th>
 * </tr>
 * <tr>
 * <td>200</td>
 * <td>请求成功（直接返回成功）</td>
 * </tr>
 * <tr>
 * <td>204</td>
 * <td>请求成功,无返回值</td>
 * </tr>
 * <tr>
 * <td>400</td>
 * <td>入参错误（如参数长度/参数限制等错误提示）</td>
 * </tr>
 * <tr>
 * <td>401</td>
 * <td>Unauthorized apiKey错误</td>
 * </tr>
 * <tr>
 * <td>403</td>
 * <td>Forbidden 验签(signature)错误</td>
 * </tr>
 * <tr>
 * <td>404</td>
 * <td>未找到对应资源（如订单不存在）</td>
 * </tr>
 * <tr>
 * <td>408</td>
 * <td>超时（timestamp已经超出处理时效）</td>
 * </tr>
 * <tr>
 * <td>500</td>
 * <td>服务器错误（给出提示）</td>
 * </tr>
 * </table>
 * 
 * @author Steven_Ge
 * 
 */
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
