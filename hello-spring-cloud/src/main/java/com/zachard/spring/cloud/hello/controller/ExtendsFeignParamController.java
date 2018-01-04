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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zachard.service.api.hello.dto.User;
import com.zachard.service.api.hello.service.ExtendsFeignParamService;
import com.zachard.spring.cloud.hello.service.UserService;

/**
 * 通过继承方式实现<code>Feign</code>方式服务调用--服务提供方Controller
 * 
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RestController
public class ExtendsFeignParamController implements ExtendsFeignParamService {
	
	private static final Logger logger = LoggerFactory.getLogger(ExtendsFeignParamController.class);

	@Autowired
	private UserService userService;
	
	/**
	 * <code>GET</code>请求中url?param=value请求方式
	 * 
	 * @param name    参数名
	 * @return        请求响应信息
	 */
	@Override
	public String requestParam(@RequestParam("name") String name) {
		logger.info("requestParam请求参数, name: {}", name);
		return "Hello: " + name;
	}

	/**
	 * <code>GET</code>请求将参数放入Header的请求方式
	 * 
	 * @param id     <code>GET</code>请求中Header请求参数
	 * @param name   <code>GET</code>请求中Header请求参数
	 * @return    请求响应体
	 */
	@Override
	public User requestHeader(@RequestHeader("id") Long id, @RequestHeader("name") String name) {
		logger.info("requestHeader请求参数, id: {}, name: {}", id, name);
		return new User(id, name);
	}

	/**
	 * <code>GET</code>请求将参数放入请求路径中的请求方式
	 * 
	 * @param id    用户ID
	 * @return
	 */
	@Override
	public User pathVariable(@PathVariable("id") Long id) {
		logger.info("pathVariable请求参数, id: {}", id);
		com.zachard.spring.cloud.hello.model.User user = userService.queryById(id);
		User res = new User(user.getId(), user.getName());
		return res;
	}

	/**
	 * <code>POST</code>请求将请求参数放入请求体请求方式
	 * 
	 * @param user   请求实体
	 * @return       请求响应
	 */
	@Override
	public String requestBody(@RequestBody User user) {
		logger.info("requestBody请求参数, id: {}, name: {}", user.getId(), user.getName());
		return "Hello, " + user.getName() + " Your ID is: " + user.getId();
	}

}
