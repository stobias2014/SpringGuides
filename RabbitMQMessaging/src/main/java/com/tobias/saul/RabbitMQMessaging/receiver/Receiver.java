package com.tobias.saul.RabbitMQMessaging.receiver;

import java.util.concurrent.CountDownLatch;

import org.springframework.stereotype.Component;

//messaging-based app needs a receiver thats responds to published messages

@Component
public class Receiver {
	
	private CountDownLatch latch = new CountDownLatch(1);
	
	public void receiveMessage(String message) {
		System.out.println("Receiver <" + message + ">");
		latch.countDown();
	}
	
	public CountDownLatch getLatch() {
		return this.latch;
	}

}
