package com.cms.config;


import java.io.IOException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitMQConfig
{
	
	@Value("${rabbitmq.queue.name}")
	private String queueName;
	
	@Value("${spring.rabbitmq.host}")
	private String host;
	
	@Value("${spring.rabbitmq.port}")
	private int port;
	
	@Value("${spring.rabbitmq.username}")
	private String userName;
	
	@Value("${spring.rabbitmq.password}")
	private String password;
	
//	@Value("${spring.rabbitmq.virtual-host}")
//	private String virtualHost;
	
	@Value("${rabbitmq.exchange.name}")
	private String exchange;
	
	@Value("${rabbitmq.routing.key}")
	private String routingKey;


	@Bean
	public ConnectionFactory connectionFactory() throws IOException{
	CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
    connectionFactory.setHost(host);
	connectionFactory.setPort(port);
	connectionFactory.setUsername(userName);
	connectionFactory.setPassword(password);
//	connectionFactory.setVirtualHost(virtualHost);
	return connectionFactory;
    
	}
	@Bean
	public Queue queue()
	{
		return new Queue(queueName, false);
	}

	

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchange);
	}
	
	@Bean
	public Binding binding()
	{
		return BindingBuilder.bind(queue())
				.to(exchange()) 
				.with(routingKey);
	}
	
	@Bean
	public MessageConverter converter()
	{
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean
	public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory)
	{
		RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(converter());
		return rabbitTemplate;
	}
	
	
	
//	@Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        return rabbitTemplate;
//    }
//
//	@Bean
//	public Declarables fanoutBindings() {
//	    Queue fanoutQueue1 = new Queue("fanout.queue1", false);
//
//	    FanoutExchange fanoutExchange = new FanoutExchange("fanout.exchange");
//
//	    return new Declarables(
//	      fanoutQueue1,
//
//	      fanoutExchange,
//	      BindingBuilder.bind(fanoutQueue1).to(fanoutExchange));
////	      BindingBuilder.bind(fanoutQueue2).to(fanoutExchange));
//	}
	
	
	
	
	
}