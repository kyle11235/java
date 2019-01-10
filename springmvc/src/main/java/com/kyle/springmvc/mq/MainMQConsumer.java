package com.kyle.springmvc.mq;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainMQConsumer {

	public static void main(String[] args) {
		// just need to run this method, message will be consumed
		// and the listener container will be running all the time
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-mq-consumer.xml");
	}

}
