package com.kyle.springmvc.mq.producer;

public interface ProducerService {

	public void sendMessage(final String message);
	
}
