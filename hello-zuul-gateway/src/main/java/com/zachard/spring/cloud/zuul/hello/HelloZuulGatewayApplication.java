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

package com.zachard.spring.cloud.zuul.hello;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * <code>Zuul</code>网关服务启动入口类
 * 
 * <pre>
 *     (1) {@link SpringCloudApplication}注解作用等同于程序同时加入{@link SpringBootApplication}注解
 *         {@link EnableDiscoveryClient}服务发现注解及{@link EnableCircuitBreaker}断路器注解
 *     (2) {@link EnableZuulProxy}注解用于构建一个<code>Zuul</code>服务端并为其安装一些反向代理过滤器, 
 *         这样<code>Zuul</code>服务端可以实现将请求转发到后端服务器. 后端服务的注册可以通过手动配置方式或是
 *         <code>DiscoveryClient</code>注册中心方式实现
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@EnableZuulProxy
@SpringCloudApplication
public class HelloZuulGatewayApplication {
	
	/**
	 * <code>Zuul</code>网关服务启动入口方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * <code>SpringApplicationBuilder</code>通过流式<code>API</code>构建<code>Spring</code>
		 * 程序及程序对应的上下文
		 */
		new SpringApplicationBuilder(HelloZuulGatewayApplication.class).web(true).run(args);
	}

}
