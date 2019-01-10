package com.kyle.springmvc.mq.producer.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.kyle.springmvc.mq.producer.ProducerService;

@Service("producerService")
public class ProducerServiceImpl implements ProducerService {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Queue mq1;

	public void sendMessage(final String message) {
		System.out.println("---------------producerService.sendMessage:" + message);
		jmsTemplate.send(mq1, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message);
			}
		});
	}

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public Queue getMq1() {
		return mq1;
	}

	public void setMq1(Queue mq1) {
		this.mq1 = mq1;
	}

}
