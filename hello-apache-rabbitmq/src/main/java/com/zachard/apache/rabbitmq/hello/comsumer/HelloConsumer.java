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

package com.zachard.apache.rabbitmq.hello.comsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * <code>RabbitMQ</code>消息代理消费者简单示例
 * 
 * <pre>
 *     (1) {@link RabbitListener}注解标记了一个<code>Rabbit</code>消息监听器并通过<code>queues</code>
 *         属性指定了需要监听的队列.  <code>queues</code>属性可以是队列名称、属性占位符的key或者表达式, 其中, 
 *         表达式将会被转换为一个队列名称或是队列对象, 队列必须存在或者被程序上下文中的{@link RabbitAdmin}对象
 *         定义为<code>Bean</code>对象
 *         
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@Component
@RabbitListener(queues = "hello")
public class HelloConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloConsumer.class);

	/**
	 * {@link RabbitHandler}注解用于标记一个方法作为<code>Rabbit</code>消息监听器({@link RabbitListener}注解的类)
	 * 接收到消息的处理方法
	 * 
	 * <pre>
	 *     (1) important: 当一个消息到达监听器之后, 将通过消息内容的类型选择一个方法对消息进行处理, 这个类型
	 *         会匹配一个单个没有注解的参数或是一个参数被{@link Payload}注解的方法. 程序必须基于消息内容的类
	 *         型只匹配到一个指定的方法用于处理消息
	 * </pre>
	 * 
	 * @param context
	 */
	@RabbitHandler
	public void process(String context) {
		logger.info("收到名称为hello的消息队列中的消息为: " + context);
	}
	
}
