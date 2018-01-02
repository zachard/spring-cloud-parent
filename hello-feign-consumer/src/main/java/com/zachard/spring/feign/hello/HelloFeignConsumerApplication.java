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

package com.zachard.spring.feign.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Configuration;

/**
 * <code>Spring Cloud Feign</code>启动类
 * 
 * <pre>
 *     (1) {@link EnableDiscoveryClient}用于开启一个{@link DiscoveryClient}的实现
 *         {@link DiscoveryClient}定义了一种通用的可以获取已经发现的服务的操作(开启发现服务的能力)
 *         项目加入spring-cloud-starter-eureka架包并配置eureka.client.serviceUrl.defaultZone注册中心
 *         地址之后, 不加入{@link EnableDiscoveryClient}似乎也没有影响
 *     (2) {@link EnableFeignClients}用于扫描被{@link FeignClient}注解的接口并将它们声明为
 *         <code>Feign Client</code>. 通过配置{@link Configuration}注解开启组件扫描指令
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class HelloFeignConsumerApplication {
	
	/**
	 * 程序入口启动方法
	 * 
	 * @param args    程序启动参数
	 */
	public static void main(String[] args) {
		SpringApplication.run(HelloFeignConsumerApplication.class, args);
	}

}
