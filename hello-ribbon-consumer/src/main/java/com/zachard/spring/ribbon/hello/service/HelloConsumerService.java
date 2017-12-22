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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
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
	 * </pre>
	 * @return    正常服务调用响应
	 */
	@HystrixCommand(fallbackMethod = "hystrixFallback")
	public String helloHystrix() {
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
	 * 断路器回调方法
	 * 
	 * @return    错误信息
	 */
	public String hystrixFallback() {
		return "Something Wrong!";
	}

}
