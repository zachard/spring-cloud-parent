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

package com.zachard.spring.config.client.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户端从Spring Config配置中心获取属性示例Controller
 * 
 * <pre>
 *     (1) {@link RefreshScope}注解表示开启refresh机制, 当在配置中心客户端请求<code>/refresh</code>
 *         的时候, 会重新从配置中心的服务端获取值
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@RefreshScope
@RestController
public class GetValueController {
	
	@Value("${description}")
	private String desc;
	
	/**
	 * 表示当前运行中的程序的上下文环境
	 */
	@Autowired
	private Environment env;
	
	/**
	 * 获取Spring Config配置中心属性
	 * 
	 * @return    Spring Config配置中心属性
	 */
	@RequestMapping(value = "/get/value", method = {RequestMethod.GET})
	public String getValue() {
		return desc;
	}
	
	/**
	 * 通过{@link Environment}对象获取配置中心服务端的属性
	 * 
	 * @return    Spring Config配置中心属性
	 */
	@RequestMapping(value = "/env/value", method = {RequestMethod.GET})
	public String getValueByEnv() {
		return env.getProperty("description", "为定义");
	}

}
