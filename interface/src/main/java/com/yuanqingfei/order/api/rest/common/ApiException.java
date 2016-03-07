package com.yuanqingfei.order.api.rest.common;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * API Exception
 * 
 * @author Steven_Ge
 * 
 */
public class ApiException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5748360010461167138L;

	public ApiException(Response response) {
		super(response);
	}

}
