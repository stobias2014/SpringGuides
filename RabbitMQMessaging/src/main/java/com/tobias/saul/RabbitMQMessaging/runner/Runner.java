package com.tobias.saul.RabbitMQMessaging.runner;

import java.util.concurrent.TimeUnit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tobias.saul.RabbitMQMessaging.RabbitMqMessagingApplication;
import com.tobias.saul.RabbitMQMessaging.receiver.Receiver;

@Component
public class Runner implements CommandLineRunner{
	
	private final RabbitTemplate rabbitTemplate;
	private final Receiver receiver;
	
	public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		this.receiver = receiver;
	}
	
	@Override
	public void run(String...args) throws Exception {
		System.out.println("--------------");
		System.out.println("Sending message....");
		
		rabbitTemplate.convertAndSend(RabbitMqMessagingApplication.getTopicexchangename(), "foo.bar.baz", "Hello from RabbitMQ!");
		System.out.println("--------------");
		receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		System.out.println("--------------");
	}

}
