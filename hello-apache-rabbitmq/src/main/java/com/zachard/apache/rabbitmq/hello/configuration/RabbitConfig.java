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

package com.zachard.apache.rabbitmq.hello.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <code>Rabbit</code>消息代理配置类
 * 
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@Configuration
public class RabbitConfig {
	
	/**
	 * 名称为<code>hello</code>的消息队列名称
	 */
	private static final String HELLO_QUEUE_NAME = "hello";
	
	/**
	 * {@link Queue}表示一个队列, 即一个用于收集信息的简单容器, 通常结合<code>AmqpAdmin</code>
	 * 一起使用
	 * 
	 * @return   指定名称的消息队列
	 */
	@Bean
	public Queue helloQueue() {
		return new Queue(HELLO_QUEUE_NAME);
	}

}
