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

package com.zachard.spring.feign.hello.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.zachard.spring.feign.hello.model.User;
import com.zachard.spring.feign.hello.service.FeignParamService;

/**
 * {@link FeignParamService}服务请求降级类
 * 
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@Component
public class FeignParamServiceFallback implements FeignParamService {

	/**
	 * 父级请求接口中方法的服务降级逻辑方法
	 * 
	 * @param name   服务请求参数
	 * @return       服务降级响应信息
	 */
	@Override
	public String requestParam(@RequestParam("name") String name) {
		return "请求名称为: " + name + " 的用户信息失败";
	}

	/**
	 * 父级请求接口中方法的服务降级逻辑方法
	 * 
	 * @param  id   请求参数
	 * @param  name 请求参数
	 * @return 服务降级响应信息
	 */
	@Override
	public User requestHeader(@RequestHeader("id") Long id, @RequestHeader("name") String name) {
		return new User(0L, "异常");
	}

	/**
	 * 父级请求接口中方法的服务降级逻辑方法
	 * 
	 * @param  请求参数
	 * @return  服务降级响应信息
	 */
	@Override
	public User pathVariable(@PathVariable("id") Long id) {
		return new User(0L, "异常");
	}

	/**
	 * 父级请求接口中方法的服务降级逻辑方法
	 * 
	 * @param  user   请求信息
	 * @return  服务降级响应信息
	 */
	@Override
	public String requestBody(@RequestBody User user) {
		return "服务降级了~";
	}

}
