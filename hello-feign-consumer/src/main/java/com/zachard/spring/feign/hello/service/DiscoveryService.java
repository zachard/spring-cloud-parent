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

package com.zachard.spring.feign.hello.service;

import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <code>zachard-service-1</code>服务请求客户端
 * 
 * <pre>
 *     (1) {@link FeignClient}用于注解接口, 将其声明为一个<code>REST</code>客户端, 如果项目中引入了ribbon
 *         模块， 则将其作为后端请求的负载均衡器, 此外, 还可以通过{@link RibbonClient}注解来配置负载均衡器
 *         注: <code>@FeignClient</code>注解中的服务名不区分大小写
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@FeignClient("zachard-service-1")
public interface DiscoveryService {
	
	/**
	 * 客户端中请求相应服务的方法调用
	 * 
	 * @return    远程服务返回信息
	 */
	@GetMapping("/discovery")
	List<ServiceInstance> discovery();

}
