/*
 *  Copyright 2015-2018 zachard, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.zachard.apache.rabbitmq.hello.producer;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <code>RabbitMQ</code>消息代理生产者简单示例
 * 
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@Component
public class HelloProducer {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloProducer.class);
	
	/**
	 * 消息需要发送到的队列名称
	 */
	private static final String QUEUE_NAME = "hello";
	
	/**
	 * {@link AmqpTemplate}定义了一系列<code>AMQP</code>协议的基本操作, 提供了同步操作的发送和接收消息的方法, 
	 * 其中{@link AmqpTemplate#convertAndSend(Object)}方法和{@link AmqpTemplate#receiveAndConvert()}
	 * 方法用于发送和接收<code>POJO</code>对象. 通常<code>AmqpTemplate</code>的实现都需要指定一个
	 * {@link MessageConverter}类型的实例对象用于与<code>AMQP</code>中字节数组类型的数据转换
	 */
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	public void send() {
		String context = "Hello, Today is " + new Date();
		logger.info("消息生产者发送的消息为: " + context);
		
		/*
		 * AmqpTemplate#convertAndSend(String key, Object message)方法用于将一个Java对象转换为
		 * 一个AMQP的消息对象并根据特定的路由key将消息发送到一个默认的Exchange之中
		 */
		rabbitTemplate.convertAndSend(QUEUE_NAME, context);
	}

}
