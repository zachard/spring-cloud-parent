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

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <code>Feign</code>重试机制请求客户端
 * 
 * <pre>
 *     注: (1) 一般对于一个服务而言, 其对应的<code>Feign</code>客户端应该只有一个, 但是这里因为需要应对各种
 *             情况的需要, 创建了多个name或是value属性相同的{@link FeignClient}
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@FeignClient("zachard-service-1")
public interface FeignRetryService {
	
	/**
	 * 请求<code>zachard-service-1</code>服务随机超时时间接口
	 * 
	 * @return    服务请求的响应
	 */
	@GetMapping("/feign/timeout")
	String timeout();

}
