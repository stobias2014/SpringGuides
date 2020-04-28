package com.tobias.saul.RabbitMQMessaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.tobias.saul.RabbitMQMessaging.receiver.Receiver;



// RabbitTemplate provides to send and receive messages
// Needs:
// 1. Configure message listener container
// 2. Declared queue, exchange, binding between them
// 3. Configure component to send messages to test listener


@SpringBootApplication
public class RabbitMqMessagingApplication {
	
	private static final String topicExchangeName = "spring-boot-exchange";
	static final String queueName = "spring-boot";
	
	@Bean
	public Queue queue() {
		return new Queue(queueName, false);
	}
	
	@Bean
	public TopicExchange topicExchange() {
		return new TopicExchange(getTopicexchangename());
	}
	
	@Bean
	public Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
	}
	
	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}
	
	@Bean
	public MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	
	public static void main(String[] args) throws InterruptedException{
		SpringApplication.run(RabbitMqMessagingApplication.class, args).close();;
	}

	public static String getTopicexchangename() {
		return topicExchangeName;
	}

}
