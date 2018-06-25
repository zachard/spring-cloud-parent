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

import java.util.concurrent.Future;

import javax.crypto.BadPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

/**
 * <code>Spring Cloud Hystrix</code>断路器测试Service类
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@Service
public class HelloConsumerService {
	
	private static final Logger logger = LoggerFactory.getLogger(HelloConsumerService.class);
	
	@Autowired
	RestTemplate restTemplate;
	
	/**
	 * <code>Spring Cloud Hystrix</code>断路器Service方法 - 同步执行的方式
	 * 
	 * <pre>
	 *     (1) {@link HystrixCommand}表示断路器命令, 定义请求无响应或是超时的回调方法等属性
	 *         fallbackMethod表示断路器的回调方法
	 *         ignoreExceptions属性表示发生某些异常时,不进入断路器方法,而是选择抛出异常
	 *         其他属性见文档...
	 *     (2) {@link CacheResult}必须与{@link HystrixCommand}注解结合使用, 用于表明
	 *         <code>HystrixCommand</code>请求结果可被缓存, 并将请求的所有参数值作为缓存的key值
	 *     (3) {@link CacheKey}与{@link CacheResult}的作用一致, 也可用于生成缓存的key, 但是它的
	 *         优先级比<code>cacheKeyMethod</code>低, 如果通过<code>cacheKeyMethod</code>指定了
	 *         生成缓存key的方法, 则{@link CacheKey}不生效
	 * </pre>
	 * @return    正常服务调用响应
	 */
	//@CacheResult(cacheKeyMethod = "generateCacheKey")
	@HystrixCommand(fallbackMethod = "hystrixFallback", ignoreExceptions= {BadPaddingException.class}, 
			commandKey = "helloHystrix", groupKey = "helloHystrixGroup", threadPoolKey = "helloHystrixThreadPool")
	public String helloHystrix(@CacheKey("id") Long id) {
		// 此HystrixCommand注解命令会同步执行
		return restTemplate.getForObject("http://zachard-service-1/discovery", String.class);
	}
	
	/**
	 * <code>Spring Cloud Hystrix</code>断路器Service方法 - 异步执行的方式
	 * 
	 * <pre>
	 *     (1) {@link AsyncResult}用于方法签名中, 表示异步执行返回的结果类型
	 *         {@link AsyncResult#invoke()}方法用户封装异步调用的逻辑
	 * </pre>
	 * 
	 * @return    正常服务响应
	 */
	@HystrixCommand
	public Future<String> helloHystrixAsync() {
		// 此HystrixCommand注解命令会异步执行
		return new AsyncResult<String>() {
			@Override
			public String invoke() {
				return restTemplate.getForObject("http://zachard-service-1/discovery", String.class);
			}
		};
		
	}
	
	/**
	 * <code>Spring Cloud Hystrix</code>断路器Service方法 - {@link Observable}响应式执行方式
	 * 
	 * <pre>
	 *     (1) {@link ObservableExecutionMode#EAGER}表示以{@link com.netflix.hystrix.HystrixCommand#observe()}
	 *         方式执行, 当<code>observe</code>被调用时, 命令会立即执行, 并且在<code>Observable</code>
	 *         每次被订阅的时候会重放它的行为
	 *         而{@link ObservableExecutionMode#LAZY}表示以{@link com.netflix.hystrix.HystrixCommand#toObservable()}
	 *         方式执行, 当<code>toObservable</code>被调用时, 命令不会执行, 只有当所有订阅者订阅它之后才执行
	 *     (2) 传统的{@link Observable}只能发射一次数据, 而通过{@link HystrixObservableCommand}实现的命令可以能获取
	 *         发射多次的{@link Observable}, 本方法即为{@link HystrixObservableCommand}的命令实现
	 *     (3) {@link Observable}用于为观察者提供订阅事件的方法
	 * </pre>
	 * 
	 * @return    正常响应
	 */
	@HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER)
	public Observable<String> helloHystrixObservable() {
		/*
		 *  create方法返回一个Observable对象
		 *  当有Subscriber订阅Observable的时候, 会触发它执行某个特定的方法
		 *  
		 *  OnSubscribe表示某种具体的操作, 比如说订阅? 
		 *  当Observable.subscribe调用时, 会调用其call方法
		 */
		return Observable.create(new OnSubscribe<String>() {
			/*
			 * Subscriber提供了获取事件发布的通知的机制, 并且允许人工手动解除对事件的订阅
			 */
			@Override
			public void call(Subscriber<? super String> observer) {
				try {
					// isUnsubscribed用于判断Subscriber是否退订事件
					if (!observer.isUnsubscribed()) {
						String response = restTemplate
								.getForObject("http://zachard-service-1/discovery", String.class);
						/*
						 *  当一个Subscriber调用Observable的subscribe方法时, 
						 *  Observable会调用Subscriber的onNext来发送项目
						 *  并且按照最佳实践, 之后应该调用Subscriber的onCompleted或onError方法退出程序
						 */
						observer.onNext(response);
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		});
	}
	
	/**
	 * 对数据进行更新操作
	 * 
	 * <pre>
	 *     (1) {@link CacheRemove}注解用于清除缓存中的数据, 其<code>commandKey</code>必须设置
	 *         并且该属性设置生成的key需与{@link CacheResult}一致
	 * </pre>
	 * @param id    更新操作请求参数
	 */
	@CacheRemove(commandKey = "generateCacheKey")
	@HystrixCommand
	public void helloCacheRemove(@CacheKey("id") Long id) {
		// 执行信息的更新操作
	}
	
	/**
	 * 断路器回调方法
	 * <pre>
	 *    (1) 通过传入{@link Throwable}参数, 获取程序处理过程中出现的异常
	 * </pre>
	 * 
	 * @return    错误信息
	 */
	public String hystrixFallback(Throwable e) {
		logger.error("请求出现异常: {}", e);
		
		/*
		 *  作为一个服务降级处理方法, 如果存在网络请求会导致回调方法出现异常, 所以也应该
		 *  通过@HystrixCommand为其指定服务降级方法
		 *  所以个人认为: 服务降级回调的方法应该是尽量稳定的代码
		 */
		return "Something Wrong!";
	}
	
	/**
	 * {@link CacheResult} 生成缓存key的方法
	 * 
	 * @param cacheKey   生成换成key所需的参数
	 * @return           缓存的key
	 */
	@SuppressWarnings("unused")
	private String generateCacheKey(String cacheKey) {
		return cacheKey;
	}

}
