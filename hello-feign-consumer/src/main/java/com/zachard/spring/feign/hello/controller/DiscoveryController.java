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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zachard.spring.feign.hello.service.DiscoveryService;

/**
 * 消费者端服务请求Controller
 * 
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RestController
public class DiscoveryController {
	
	@Autowired
	private DiscoveryService discoveryService;
	
	/**
	 * <code>Feign Client</code>服务消费入口
	 * 
	 * @return    请求服务响应对象
	 */
	@GetMapping("/discovery")
	public List<ServiceInstance> discovery() {
		return discoveryService.discovery();
	}

}
