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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
	
	/**
	 * 注解实现请求合并Service方法 -- 根据用户编号查询单个用户信息方法
	 * 
	 * <pre>
	 *     (1) {@link HystrixCollapser}注解指定了请求合并相关的属性, 时间窗口大小、批量请求方法等
	 *     (2) 思考: 设置100ms内的请求进行合并, 那么100ms内10个请求会合并调用<code>batchMethod</code>
	 *         中指定的方法, 100ms内只有1个请求会调用<code>batchMethod</code>方法
	 * </pre>
	 * 
	 * @param id    请求参数
	 * @return      用户信息实体
	 */
	@HystrixCollapser(batchMethod = "helloCollapserBatch", collapserProperties = {
			@HystrixProperty(name="timerDelayInMilliseconds", value="100")
	})
	public User helloCollapser(Long id) {
		logger.info("根据用户编号查询用户信息, helloCollapser查询参数: {}", id);
		return restTemplate.getForObject("http://zachard-service-1/user/{1}", User.class, id);
	}
	
	/**
	 * 注解实现请求合并Service方法 -- 进行批量查询处理的方法(请求合并之后实际调用的方法)
	 * 
	 * @param idList   请求合并后批量请求的参数列表
	 * @return         请求合并批量查询后的批量用户信息
	 */
	@SuppressWarnings("unchecked")
	@HystrixCommand
	public List<User> helloCollapserBatch(List<Long> idList) {
		logger.info("根据用户编号列表查询用户信息, helloCollapserBatch请求参数: {}", StringUtils.join(idList, ","));
		return restTemplate.getForObject("http://zachard-service-1/users/{1}", 
				List.class, StringUtils.join(idList, ","));
	}

}
