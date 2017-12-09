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

package com.zachard.spring.cloud.hello.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟需要注册到<code>Eureka</code>注册中心上的服务
 * 
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RestController
public class EurekaDiscoveryClientController {
	
	@Autowired
	private DiscoveryClient client;
	
	/**
	 * 用于发现服务的请求接口
	 * 
	 * @return    第一个服务ID对应的服务列表
	 */
	@GetMapping("/discovery")
	public List<ServiceInstance> discovery() {
		// <code>DiscoveryClient#getServices</code>用于获取所有服务ID列表
		List<String> services = client.getServices();
		
		if (!services.isEmpty()) {
			// <code>DiscoveryClient#getInstances(String)</code>通过服务ID获取对应服务列表
			return client.getInstances(services.get(0));
		}
		
		return Collections.emptyList();
	}

}
