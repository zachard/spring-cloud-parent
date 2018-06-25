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

package com.zachard.spring.cloud.hello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <code>Feign</code>客户端请求服务超时重试机制控制器
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RestController
@RequestMapping("/feign")
public class FeignRetryController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeignRetryController.class);
	
	/**
	 * 服务的超时时间, 从配置文件读取
	 */
	@Value("${service.random.timeout}")
	private int timeoutValue;
	
	@Value("${server.port}")
	private int port;
	
	/**
	 * 随机休眠一定时间服务
	 * 
	 * @return   响应细信息
	 * @throws InterruptedException
	 */
	@GetMapping("/timeout")
	public String timeout() throws InterruptedException {
		//int sleepTime = new Random().nextInt(3000);
		logger.info("线程休眠时间为: {}, 当前实例端口号为: {}", timeoutValue, port);
		Thread.sleep(timeoutValue);
		
		return "请求的服务实例端口号为: " + port;
	}

}
