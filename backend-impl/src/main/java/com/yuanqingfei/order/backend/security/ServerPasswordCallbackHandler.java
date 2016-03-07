package com.yuanqingfei.order.backend.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;

public class ServerPasswordCallbackHandler implements CallbackHandler {
	@Resource(name="wsconf")
	WSConfigProperty configProperty;
	@Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        if (pc.getIdentifier().equals(configProperty.getUserName())) {
            //设置密码
            //这个密码和客户端发送的密码进行比较
            //如果和客户端不同将抛出org.apache.ws.security.WSSecurityException
            pc.setPassword(configProperty.getPassword());
        } else {
        	throw new SecurityException("no this user: " + pc.getIdentifier());
        }
    }
}
