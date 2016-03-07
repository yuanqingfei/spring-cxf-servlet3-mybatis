package com.yuanqingfei.order.api.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientPasswordCallbackHandler implements CallbackHandler {

	@Autowired
	private ConfigProperty configProperty;

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
		pc.setPassword(configProperty.getPassword());
	}
}
