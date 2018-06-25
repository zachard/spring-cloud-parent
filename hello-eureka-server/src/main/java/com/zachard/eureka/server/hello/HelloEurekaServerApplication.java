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

package com.zachard.eureka.server.hello;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.eureka.server.EurekaServerAutoConfiguration;

/**
 * <code>Eureka</code>服务端(服务注册中心)启动类
 * 
 * <pre>
 *     (1) {@link EnableEurekaServer}注解用于激活基于
 *         {@link EurekaServerAutoConfiguration}自动配置的<code>Eureka Server</code>配置
 *     (2) {@link SpringBootApplication}注解相关作用见<code>Spring Boot</code>学习资料
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@EnableEurekaServer
@SpringBootApplication
public class HelloEurekaServerApplication {
	
	/**
	 * <code>Eureka</code>服务端启动入口方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		 * <code>SpringApplicationBuilder</code>提供了一种流式的<code>API</code>
		 * 创建<code>SpringApplication</code>及<code>ApplicationContext</code>实例
		 * 
		 * 另一个常用的用法用于应用程序的环境属性及<code>profiles</code>
		 * 
		 * <code>SpringApplicationBuilder#web</code>表明当前创建的应用程序环境是否为web环境
		 */
		new SpringApplicationBuilder(HelloEurekaServerApplication.class)
		     .web(true).run(args);
	}

}
