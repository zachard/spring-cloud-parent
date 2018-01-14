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

package com.zachard.apache.rabbitmq.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zachard.apache.rabbitmq.hello.producer.HelloProducer;

/**
 * <code>RabbitMQ</code>应用程序主测试类
 * 
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HelloRabbitMQApplication.class)
public class HelloRabbitMQApplicationTest {
	
	@Autowired
	private HelloProducer producer;
	
	/**
	 * 生产者生产消息测试方法
	 */
	@Test
	public void producer() {
		producer.send();
	}

}
