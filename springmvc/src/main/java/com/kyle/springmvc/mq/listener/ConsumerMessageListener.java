package com.kyle.springmvc.mq.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.jms.support.JmsUtils;

public class ConsumerMessageListener implements MessageListener {

	public void onMessage(Message message) {
		TextMessage textMsg = (TextMessage) message;
		try {
			System.out.println("------------ConsumerMessageListener.onMessage:" + textMsg.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}

		// below is used to test redelivery
		throw JmsUtils.convertJmsAccessException(new JMSException("test resend"));
	}
}