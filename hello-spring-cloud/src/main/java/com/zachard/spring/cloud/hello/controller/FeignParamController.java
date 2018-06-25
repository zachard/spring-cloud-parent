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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zachard.spring.cloud.hello.model.User;
import com.zachard.spring.cloud.hello.service.UserService;

/**
 * <code>Feign</code>服务提供者--请求参数绑定示例
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RestController
@RequestMapping("/feign")
public class FeignParamController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * <code>GET</code>请求中url?param=value请求方式
	 * 
	 * @param name    参数名
	 * @return        请求响应信息
	 */
	@GetMapping("/requestParam")
	public String requestParam(@RequestParam String name) {
		return "Hello: " + name;
	}
	
	/**
	 * <code>GET</code>请求将参数放入Header的请求方式
	 * 
	 * @param id     <code>GET</code>请求中Header请求参数
	 * @param name   <code>GET</code>请求中Header请求参数
	 * @return    请求响应体
	 */
	@GetMapping("/requestHeader")
	public User requestHeader(@RequestHeader Long id, @RequestHeader String name) {
		return new User(id, name);
	}
	
	/**
	 * <code>GET</code>请求将参数放入请求路径中的请求方式
	 * 
	 * @param id    用户ID
	 * @return
	 */
	@GetMapping("/pathVariable/{id}")
	public User pathVariable(@PathVariable("id") Long id) {
		return userService.queryById(id);
	}
	
	/**
	 * <code>POST</code>请求将请求参数放入请求体请求方式
	 * 
	 * @param user   请求实体
	 * @return       请求响应
	 */
	@PostMapping("/requestBody")
	public String requestBody(@RequestBody User user) {
		return "Hello, " + user.getName() + " Your ID is: " + user.getId();
	}

}
