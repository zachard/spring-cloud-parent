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

package com.zachard.spring.ribbon.hello.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zachard.spring.ribbon.hello.model.User;
import com.zachard.spring.ribbon.hello.service.UserService;

/**
 * 用户模块请求相关Controller
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RestController
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;

	/**
	 * 根据用户编号查询单个用户信息
	 * 
	 * @param id    用户编号
	 * @return
	 */
	@GetMapping("/user/{id}")
	public User query(@PathVariable("id") Long id) {
		logger.info("根据用户编号查询用户详情, query请求参数: {}", id);
		//return userService.queryById(id);
		return userService.helloCollapser(id);
	}
	
	/**
	 * 根据用户编号列表查询用户信息列表信息
	 * 
	 * @param ids    用户编号列表
	 * @return       用户信息列表
	 */
	@GetMapping("/users/{ids}")
	public List<User> listAll(@PathVariable("ids") String ids) {
		logger.info("根据用户编号列表查询用户信息, listAll请求参数: {}", ids);
		return userService.queryListByIds(ids);
	}
}
