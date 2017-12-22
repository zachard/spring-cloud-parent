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

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.zachard.spring.ribbon.hello.service.HelloConsumerService;

import rx.Observable;

/**
 * 自定义<code>Hystrix</code>命令
 * 
 * <pre>
 *     (1) {@link HystrixCommand}用于封装在执行过程中存在未知功能性风险(通常存在于跨网络间的服务调用)的代码, 
 *         并且提供故障及调用延迟容错、性能指标的捕获与统计、断路器及舱壁等功能。这个命令本质上是一个阻塞命令,
 *         但是提供了{@link Observable}来实现响应式执行方式
 *     (2) 执行方式: 
 *         同步执行: <code>String response = new CustomHystrixCommand(restTemplate).execute();</code>
 *         异步执行: <code>Future<String> future = new CustomHystrixCommand(restTemplate).queue();</code>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
public class CustomHystrixCommand extends HystrixCommand<String> {
	
	private RestTemplate restTemplate;

	public CustomHystrixCommand(Setter setter, RestTemplate restTemplate) {
		super(setter);
		this.restTemplate = restTemplate;
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
	protected String run() throws Exception {
		return restTemplate.getForObject("http://zachard-service-1/discovery", String.class);
	}

}
