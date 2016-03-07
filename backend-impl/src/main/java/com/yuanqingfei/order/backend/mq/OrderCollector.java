package com.yuanqingfei.order.backend.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.yuanqingfei.order.backend.mybatis.OrderMapper;

public class OrderCollector implements MessageListener {
	
	@Autowired
	private OrderMapper orderMapper;

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			try {
				System.out.println("receive: " + ((TextMessage) message).getText());
			} catch (JMSException ex) {
				throw new RuntimeException(ex);
			}
		} else {
			throw new IllegalArgumentException("Message must be of type TextMessage");
		}

	}

}
