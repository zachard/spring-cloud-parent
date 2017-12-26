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

package com.zachard.spring.ribbon.hello.command;

import java.util.List;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.zachard.spring.ribbon.hello.model.User;
import com.zachard.spring.ribbon.hello.service.HelloConsumerService;
import com.zachard.spring.ribbon.hello.service.UserService;

/**
 * 批量请求合并处理命令
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
public class UserBatchCommand extends HystrixCommand<List<User>> {
	
	private UserService userService;
	private String ids;

	/**
	 * 用户批量请求合并命令构造方法
	 * 
	 * @param userService   用户Service类
	 * @param ids           查询参数
	 */
	public UserBatchCommand(UserService userService, String ids) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("userServiceCommand")));
		this.userService = userService;
		this.ids = ids;
	}

	/**
	 * 封装在执行过程中存在未知功能性风险的代码, 当调用{@link HystrixCommand#execute()}方法
	 * 或是{@link HystrixCommand#queue()}方法时, 会执行此方法
	 * 
	 * <pre>
	 *     对比一下{@link HelloConsumerService}类, 当调用{@link HystrixCommand#execute()}
	 *     方法时, 作用与{@link HelloConsumerService#helloHystrix()}一致, 当调用{@link HystrixCommand#queue()}
	 *     方法时, 作用与{@link HelloConsumerService#helloHystrixAsync()}一致。相当于在<code>Command</code>
	 *     中实现了<code>Service</code>层的功能
	 * </pre>
	 */
	@Override
	protected List<User> run() throws Exception {
		return userService.queryListByIds(ids);
	}

}
