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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zachard.spring.feign.hello.model.User;
import com.zachard.spring.feign.hello.service.impl.FeignParamServiceFallback;

/**
 * <code>Feign</code>请求服务参数格式Service接口
 * 
 * <pre>
 *     (1) {@link FeignClient}注解中的<b>fallback</b>属性指定了服务降级对应类
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@FeignClient(value = "zachard-service-1", fallback = FeignParamServiceFallback.class)
public interface FeignParamService {
	
	/**
	 * <code>GET</code>请求中url?name=value请求参数格式
	 * 
	 * <pre>
	 *     (1) 注: <code>@RequestParam</code>注解中必须执行value属性
	 *     (2) {@link FeignClient}注解客户端中的方法不能使用{@link GetMapping}这种注解
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/feign/requestParam", method = {RequestMethod.GET})
	String requestParam(@RequestParam("name") String name);
	
	/**
	 * <code>GET</code>请求将参数放入Header的请求方式
	 * 
	 * <pre>
	 *     (1) 注: <code>@RequestHeader</code>中必须指定value属性值
	 *     (2) {@link FeignClient}注解客户端中的方法不能使用{@link GetMapping}这种注解
	 * </pre>
	 * 
	 * @param id     <code>GET</code>请求中Header请求参数
	 * @param name   <code>GET</code>请求中Header请求参数
	 * @return    请求响应体
	 */
	@RequestMapping(value = "/feign/requestHeader", method = {RequestMethod.GET})
	User requestHeader(@RequestHeader("id") Long id, @RequestHeader("name") String name);
	
	/**
	 * <code>GET</code>请求将参数放入请求路径中的请求方式
	 * 
	 * <pre>
	 *     (1) 注: <code>@PathVariable</code>中必须指定属性的值
	 *     (2) {@link FeignClient}注解客户端中的方法不能使用{@link GetMapping}这种注解
	 * </pre>
	 * 
	 * @param id    用户ID
	 * @return
	 */
	@RequestMapping(value = "/feign/pathVariable/{id}", method = {RequestMethod.GET})
	User pathVariable(@PathVariable("id") Long id);
	
	/**
	 * <code>POST</code>请求将请求参数放入请求体请求方式
	 * 
	 * <pre>
	 *     (1) {@link FeignClient}注解客户端中的方法不能使用{@link PostMapping}这种注解
	 * </pre>
	 * 
	 * @param user   请求实体
	 * @return       请求响应
	 */
	@RequestMapping(value = "/feign/requestBody", method = {RequestMethod.POST})
	String requestBody(@RequestBody User user);

}
