package com.kyle.springmvc.mq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kyle.springmvc.mq.producer.ProducerService;

public class MainMQ {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-mq.xml");
		ProducerService producer = (ProducerService) context.getBean("producerService");
		int counter = 0;
		while (counter++ < 10000) {
			producer.sendMessage("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa " + counter);
		}
		// producer will be blocked by memory limit
		// producer will continue when memory is free
		// exception could be thrown if configured in activemq.xml, you can try
		// catch send method of producer
	}

}
