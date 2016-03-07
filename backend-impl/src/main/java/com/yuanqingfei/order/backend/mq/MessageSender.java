package com.yuanqingfei.order.backend.mq;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    private JmsTemplate jmsTemplate;
    
    public MessageSender(){
    }

    public MessageSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(final String text) {
        jmsTemplate.convertAndSend(text);
        System.out.println("send: " + text);
    }
}
