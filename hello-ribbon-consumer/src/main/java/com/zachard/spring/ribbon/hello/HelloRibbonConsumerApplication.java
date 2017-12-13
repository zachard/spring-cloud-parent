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

package com.zachard.spring.ribbon.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * <code>Spring Ribbon<code>服务消费者示例启动类
 * 
 * <pre>
 *     (1) {@link EnableDiscoveryClient}用于开启一个{@link DiscoveryClient}的实现
 *         {@link DiscoveryClient}定义了一种通用的可以获取已经发现的服务的操作(开启发现服务的能力)
 *         项目加入spring-cloud-starter-eureka架包并配置eureka.client.serviceUrl.defaultZone注册中心
 *         地址之后, 不加入{@link EnableDiscoveryClient}似乎也没有影响
 *     (2) {@link SpringBootApplication}注解的作用见<code>Spring Boot</code>学习代码
 *     (3) {@link EnableCircuitBreaker}注解表示在程序中开启断路器功能
 *     (4) {@link SpringCloudApplication}注解作用等同于程序同时加入{@link SpringBootApplication}注解
 *         {@link EnableDiscoveryClient}服务发现注解及{@link EnableCircuitBreaker}断路器注解
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
//@SpringCloudApplication
public class HelloRibbonConsumerApplication {
	
	/**
	 * <pre>
	 *     (1) {@link RestTemplate}是<code>Spring</code>同步客户端HTTP访问的主要类
	 *         它基于<code>RESTful</code>的原则简化了与HTTP服务器的通信, 程序代码只需要提供
	 *         请求的<code>URL</code>及期望得到的结果类型即可。
	 *         而HTTP的连接等问题交由<code>RestTemplate</code>内部处理
	 *     (2) {@link LoadBalanced}通常用于注解<code>RestTemplate</code>类型的<code>Bean</code>
	 *         对象, 表示采用{@link LoadBalancerClient}来配置<code>RestTemplate</code>
	 *         <code>LoadBalancerClient</code>表示通过客户端实现负载均衡
	 * </pre>
	 * 
	 * @return
	 */
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * <code>Spring Ribbon<code>服务消费者示例启动方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(HelloRibbonConsumerApplication.class, args);
	}

}
