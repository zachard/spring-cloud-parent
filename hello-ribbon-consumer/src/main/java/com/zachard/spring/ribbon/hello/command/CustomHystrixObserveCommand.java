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

import com.netflix.hystrix.HystrixObservableCommand;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

/**
 * {@link HystrixObservableCommand}的自定义实现, 用于实现发射多次{@link Observable}
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
public class CustomHystrixObserveCommand extends HystrixObservableCommand<String> {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomHystrixObserveCommand.class);
	
	private RestTemplate restTemplate;

	/**
	 * 构造函数
	 * 
	 * @param setter    {@link HystrixObservableCommand}构造函数流式参数接口
	 * @param restTemplate   rest请求工具
	 */
	public CustomHystrixObserveCommand(Setter setter, RestTemplate restTemplate) {
		super(setter);
		this.restTemplate = restTemplate;
	}

	/**
	 * 封装具体的代码逻辑, 当调用{@link HystrixObservableCommand#observe()}或是{@link HystrixObservableCommand#toObservable()}
	 * 方法时, 将执行此方法
	 */
	@Override
	protected Observable<String> construct() {
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
	 * {@link HystrixObservableCommand}服务降级处理方法
	 * <pre>
	 *     (1) 在服务降级方法逻辑中, 通过{@link #getExecutionException()}获取程序处理过程中出现的异常
	 * </pre>
	 * 
	 */
	@Override
	protected Observable<String> resumeWithFallback() {
		logger.error("请求处理出现异常: {}", getExecutionException());
		return Observable.empty();
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
		return "cacheKey";
	}

}
