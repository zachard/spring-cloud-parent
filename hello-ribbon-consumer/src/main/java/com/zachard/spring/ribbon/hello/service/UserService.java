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

package com.zachard.spring.ribbon.hello.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.zachard.spring.ribbon.hello.model.User;

/**
 * 用户模块操作Service类
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 根据用户编号查询
	 * 
	 * @param id    用户编号
	 * @return      用户信息
	 */
	public User queryById(Long id) {
		logger.info("根据用户编号查询用户信息, queryById查询参数: {}", id);
		return restTemplate.getForObject("http://zachard-service-1/user/{1}", User.class, id);
	}
	
	/**
	 * 根据用户编号列表查询用户列表编号信息列表
	 * 
	 * @param ids    用户编号列表
	 * @return       用户信息列表
	 */
	@SuppressWarnings("unchecked")
	public List<User> queryListByIds(String ids) {
		logger.info("根据用户编号列表查询用户信息列表, queryListByIds请求参数: {}", ids);
		return restTemplate.getForObject("http://zachard-service-1/users/{1}", List.class, ids);
	}

}
