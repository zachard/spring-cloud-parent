/*
 *  Copyright 2015-2017 zachard, Inc.
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

package com.zachard.spring.ribbon.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.zachard.spring.ribbon.hello.service.HelloConsumerService;

/**
 * <code>Spring Ribbon</code>服务消费者Controller示例
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RestController
public class HelloConsumerController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	HelloConsumerService helloConsumerService;
	
	/**
	 * <code>Spring Ribbon</code>服务消费者请求示例
	 * 
	 * @return    响应实体
	 */
	@GetMapping(value = "/consumer/get")
	public String helloConsumer() {
		/*
		 * getForEntity方法通过对指定URL发起GET请求并检索获取对应的实体,响应被转换
		 * 并存储在ResponseEntity类型的实体之中
		 * 
		 * 注: (1) RestTemplate消费注册中心服务的方式, 消费者必须和服务提供者注册到同一注册中心
		 */
//		return restTemplate.getForEntity("http://zachard-service-1/discovery", String.class)
//				.getBody();
		
		// RestTemplate处理GET请求的另外一种方式
		return restTemplate.getForObject("http://zachard-service-1/discovery", String.class);
	}
	
	/**
	 * <code>Spring Hystrix</code>断路器请求入口
	 * 
	 * @return    响应信息
	 */
	@GetMapping("/hystrix/get")
	public String helloHystrix() {
		return helloConsumerService.helloHystrix();
	}

}
