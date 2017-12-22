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

package com.zachard.spring.ribbon.hello.service;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

/**
 * <code>Spring Cloud Hystrix</code>断路器测试Service类
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@Service
public class HelloConsumerService {
	
	@Autowired
	RestTemplate restTemplate;
	
	/**
	 * <code>Spring Cloud Hystrix</code>断路器Service方法 - 同步执行的方式
	 * 
	 * <pre>
	 *     (1) {@link HystrixCommand}表示断路器命令, 定义请求无响应或是超时的回调方法等属性
	 *         fallbackMethod表示断路器的回调方法
	 *         ignoreExceptions属性表示发生某些异常时,不进入断路器方法,而是选择抛出异常
	 *         其他属性见文档...
	 * </pre>
	 * @return    正常服务调用响应
	 */
	@HystrixCommand(fallbackMethod = "hystrixFallback")
	public String helloHystrix() {
		// 此HystrixCommand注解命令会同步执行
		return restTemplate.getForObject("http://zachard-service-1/discovery", String.class);
	}
	
	/**
	 * <code>Spring Cloud Hystrix</code>断路器Service方法 - 异步执行的方式
	 * 
	 * <pre>
	 *     (1) {@link AsyncResult}用于方法签名中, 表示异步执行返回的结果类型
	 *         {@link AsyncResult#invoke()}方法用户封装异步调用的逻辑
	 * </pre>
	 * 
	 * @return    正常服务响应
	 */
	@HystrixCommand
	public Future<String> helloHystrixAsync() {
		// 此HystrixCommand注解命令会异步执行
		return new AsyncResult<String>() {
			@Override
			public String invoke() {
				return restTemplate.getForObject("http://zachard-service-1/discovery", String.class);
			}
		};
		
	}
	
	/**
	 * 断路器回调方法
	 * 
	 * @return    错误信息
	 */
	public String hystrixFallback() {
		return "Something Wrong!";
	}

}
