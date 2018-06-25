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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
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
	
	private static final Logger logger = LoggerFactory.getLogger(CustomHystrixCommand.class);
	
	/**
	 * 考虑到清楚缓存, 将CommandKey作为一个只读的属性
	 */
	private static final HystrixCommandKey COMMAND_KEY = HystrixCommandKey.Factory.asKey("customHystrixCommand");
	
	/**
	 * 缓存的key值, 实际不为硬编码, 而是根据某种方式获取
	 */
	private static final String CACHE_KEY = "cacheKey";
	
	private RestTemplate restTemplate;

	/**
	 * 自定义<code>HystrixCommand</code>命令实现方式
	 * 
	 * @param restTemplate
	 */
	public CustomHystrixCommand(RestTemplate restTemplate) {
		/*
		 * withGroupKey用于设置命令所属组名, andCommandKey用于设置命令名, andThreadPoolKey用于指定执行命令的线程池
		 * 注: (1) 只有withGroupKey方法可以创建Setter实例, 并且命令分组为必要属性
		 *     (2) 默认情况下, Hystrix命令默认的线程划分是根据命令分组实现
		 */
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("customHystrixCommandGroup"))
				.andCommandKey(COMMAND_KEY)
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("customThreadPool")));
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
	
	/**
	 * {@link HystrixCommand}服务降级逻辑处理方法
	 * 
	 * <pre>
	 *     (1) 当{@link #run()}方法执行过程中出现错误、超时、线程池拒绝、断路器熔断等情况时, 执行的方法
	 *     (2) 在服务降级处理逻辑方法中, 通过{@link #getExecutionException()}获取程序处理过程中发生的异常
	 * </pre>
	 */
	@Override
	protected String getFallback() {
		logger.error("请求出现异常: {}", getExecutionException());
		return "Something Wrong!";
	}
	
	/**
	 * 针对高并发情况下, 开启<code>Hystrix</code>请求缓存
	 * <pre>
	 * </pre>
	 * 
	 * @return  请求缓存的key
	 */
	@Override
	protected String getCacheKey() {
		// 实际不为硬编码, 而是根据某种方式获取
		return CACHE_KEY;
	}
	
	/**
	 * 清除<code>Hystrix</code>的缓存
	 * 
	 * <pre>
	 *     (1) 调用方式: <code>CustomHystrixCommand.flushCache</code>
	 *                  清除当前命令下对应的缓存的key下面的缓存
	 * </pre>
	 */
	public static void flushCache() {
		/*
		 *  根据缓存的id来清楚缓存
		 *  
		 *  clear方法用于清除指定缓存key的缓存
		 */
		HystrixRequestCache.getInstance(COMMAND_KEY, 
				HystrixConcurrencyStrategyDefault.getInstance()).clear(CACHE_KEY);
	}

}
