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

package com.zachard.spring.feign.hello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zachard.spring.feign.hello.service.FeignRetryService;

/**
 * <code>Feign</code>客户端超时请求重试请求入口
 * 
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RestController
public class FeignRetryController {
	
	private static final Logger logger = LoggerFactory.getLogger(FeignRetryController.class);
	
	@Autowired
	private FeignRetryService feignRetryService;
	
	/**
	 * <code>Feign</code>客户端超时自动重试请求接口
	 * 
	 * <pre>
	 *     注: 在application.properties中配置的接口请求超时时间为: 
	 * </pre>
	 * 
	 * @return
	 */
	@RequestMapping("/auto/retry")
	public String autoRetry() {
		long start = System.currentTimeMillis();
		String res = feignRetryService.timeout();
		long end = System.currentTimeMillis();
		logger.info("请求zachard-service-1随机超时时间接口耗时: {}", end - start);
		
		return res;
	}

}
