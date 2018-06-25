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

package com.zachard.service.api.hello.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zachard.service.api.hello.dto.User;

/**
 * 通过在一个公共的模块中定义一个Service, 然后服务提供者实现该接口(将其作为Controller父类), 而服务消费者
 * 继承该接口并将接口声明为<code>Feign</code>客户端来实现接口远程调用(将其作为Service父类)
 * 
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RequestMapping("/extends/feign")
public interface ExtendsFeignParamService {
	
	/**
	 * <code>GET</code>请求中url?param=value请求方式
	 * 
	 * <pre>
	 *     (1) 注意<code>@RequestParam</code>的value属性不能为空
	 * </pre>
	 * 
	 * @param name    参数名
	 * @return        请求响应信息
	 */
	@GetMapping("/requestParam")
	String requestParam(@RequestParam("name") String name);
	
	/**
	 * <code>GET</code>请求将参数放入Header的请求方式
	 * 
	 * <pre>
	 *     (1) 注意<code>@RequestHeader</code>的属性不能为空
	 * </pre>
	 * 
	 * @param id     <code>GET</code>请求中Header请求参数
	 * @param name   <code>GET</code>请求中Header请求参数
	 * @return    请求响应体
	 */
	@GetMapping("/requestHeader")
	User requestHeader(@RequestHeader("id") Long id, @RequestHeader("name") String name);
	
	/**
	 * <code>GET</code>请求将参数放入请求路径中的请求方式
	 * 
	 * @param id    用户ID
	 * @return
	 */
	@GetMapping("/pathVariable/{id}")
	User pathVariable(@PathVariable("id") Long id);
	
	/**
	 * <code>POST</code>请求将请求参数放入请求体请求方式
	 * 
	 * @param user   请求实体
	 * @return       请求响应
	 */
	@PostMapping("/requestBody")
	String requestBody(@RequestBody User user);

}
