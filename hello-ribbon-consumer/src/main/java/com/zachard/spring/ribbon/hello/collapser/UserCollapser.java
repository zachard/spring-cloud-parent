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

package com.zachard.spring.ribbon.hello.collapser;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.zachard.spring.ribbon.hello.command.UserBatchCommand;
import com.zachard.spring.ribbon.hello.model.User;
import com.zachard.spring.ribbon.hello.service.UserService;

/**
 * 对于在一定时间窗口内的多个根据用户编号查询用户详情的请求合并处理器
 * 
 * <pre>
 *     (1) {@link HystrixCollapser}用于将一定时间窗口中多个(数值可以设置)相同的请求合并到一个
 *         {@link HystrixCommand}命令中执行
 *         {@link HystrixCollapser}将一定时间窗口中对于<code>command</code>中<code>execute/queue</code>
 *         方法的调用合并为一次后端调用
 *         时间窗口以毫秒为单位, 默认为10ms
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
public class UserCollapser extends HystrixCollapser<List<User>, User, Long>{
	
	private UserService userService;
	private Long id;
	
	/**
	 * 自定义请求合并处理器构造器
	 * 
	 * @param userService    用户Service
	 * @param id             用户编号--查询参数
	 */
	public UserCollapser(UserService userService, Long id) {
		/*
		 * (1) HystrixCollapserKey代表一个用于监测、断路器、指标发布、缓存及其他用途的键值
		 * (2) HystrixCollapserProperties表示一个HystrixCollapser的属性对象,其
		 *     withTimerDelayInMilliseconds方法设置请求合并的时间窗口
		 */
		super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userCollapser"))
				.andCollapserPropertiesDefaults(
						HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
		this.userService = userService;
		this.id = id;
	}

	/**
	 * 获取用于传入{@link HystrixCommand}中的请求参数
	 * 
	 * <p>
	 *     表示获取从构造器中传入的参数并由此返回
	 * </p>
	 * 如果需要返回多个参数, 可以考虑用对象进行封装, 或返回数组
	 * 
	 * @return  请求参数
	 */
	@Override
	public Long getRequestArgument() {
		return id;
	}

	/**
	 * 用于创建一个用于执行批量请求的{@link HystrixCommand}{@code <BatchReturnType>}命令的工厂方法
	 * <p>
	 *   方法每次调用时都应该重新创建一个对象, 而不是返回一个相同的对象
	 * </p>
	 * <p>
	 *   用于将请求参数传入到需要执行的命令中的参数之中
	 * </p>
	 * <p>
	 *   如果一个批量请求或是许多请求需要分发到多个命令之中, 可以参考{@link #shardRequests(Collection)}实现
	 * </p>
	 * 实现需要注意的事项: 此方法应该快速返回(ie: <1ms), 否则后续的批量处理请求会阻塞定时器
	 * 
	 * @param  requests  保存延迟窗口中收集到的所有获取单个请求
	 * @return  用户执行批量请求的命令
	 */
	@Override
	protected HystrixCommand<List<User>> 
	    createCommand(Collection<CollapsedRequest<User, Long>> requests) {
		// 请求合并的实现方式: 将一定时间窗口内的多个请求合并,并通过一个批量执行命令来执行
		String ids = StringUtils.join(
				requests.stream()
				    .map(CollapsedRequest::getArgument)
				    .collect(Collectors.toList()), 
				    ",");
		
		return new UserBatchCommand(userService, ids);
	}

	/**
	 * 当{@link #createCommand(Collection)}方法创建的{@link HystrixCommand}{@code <BatchReturnType>}命令执行之后, 
	 * 将{@code <BatchReturnType>}映射为{@code CollapsedRequest<ResponseType, RequestArgumentType>}对象列表
	 * 
	 * @param  batchResponse  {@link #createCommand}创建的{@link HystrixCommand}{@code <BatchReturnType>}
	 *                        对象返回的类型
	 * @param  requests   	包含了所有合并处理的请求
	 */
	@Override
	protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Long>> requests) {
		int count = 0;
		
		// 遍历批量执行结果, 为每个合并前的单个请求设置返回结果
		for (CollapsedRequest<User, Long> collapsedRequest : requests) {
			User user = batchResponse.get(count++);
			collapsedRequest.setResponse(user);
		}
		
	}

}
