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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zachard.service.api.hello.dto.User;
import com.zachard.spring.feign.hello.service.ExtendsFeignParamService;

/**
 * 通过继承方式实现<code>Feign</code>方式服务调用请求入口
 * 
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RestController
@RequestMapping("/extends")
public class ExtendsFeignParamController {
	
	@Autowired
	private ExtendsFeignParamService extendsFeignParamService;
	
	/**
	 * <code>GET</code>请求中url?name=value请求参数格式
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping("/requestParam")
	public String requestParam() {
		return extendsFeignParamService.requestParam("章三");
	}
	
	/**
	 * <code>GET</code>请求将参数放入Header的请求方式
	 * 
	 * @param id     <code>GET</code>请求中Header请求参数
	 * @param name   <code>GET</code>请求中Header请求参数
	 * @return    请求响应体
	 */
	@GetMapping("/requestHeader")
	public User requestHeader() {
		return extendsFeignParamService.requestHeader(2L, "王武");
	}
	
	/**
	 * <code>GET</code>请求将参数放入请求路径中的请求方式
	 * 
	 * @param id    用户ID
	 * @return
	 */
	@GetMapping("/pathVariable")
	public User pathVariable() {
		return extendsFeignParamService.pathVariable(1L);
	}
	
	/**
	 * <code>POST</code>请求将请求参数放入请求体请求方式
	 * 
	 * @param user   请求实体
	 * @return       请求响应
	 */
	@GetMapping("/requestBody")
	public String requestBody() {
		return extendsFeignParamService.requestBody(new User(3L, "卓七"));
	}

}
