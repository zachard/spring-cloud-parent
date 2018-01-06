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

package com.zachard.spring.feign.hello.configuration;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import feign.Feign;
import feign.Feign.Builder;

/**
 * 禁用<code>Hystrix</code>功能的配置类
 * 
 * <pre>
 *     (1) {@link Configuration}注解将类声明为一个配置类
 *     (2) 禁用<code>Hystrix</code>配置的使用方法为将其作为{@link FeignClient}中<code>configuration</code>
 *         属性的值
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@Configuration
public class DisableHystrixConfiguration {
	
	/**
	 * 创建一个{@link Builder}对象
	 * 
	 * <pre>
	 *     (1) {@link Scope}注解表示一个实例的生命周期, 如单例、原型等
	 *     (2) 当{@link Scope}注解结合{@link Component}注解在注解类型定义上使用时, 用于表示注解实例中的
	 *         作用域名称???
	 *     (3) 当{@link Scope}注解结合{@link Bean}注解在方法上使用时, 用于表示方法返回的实例的作用域名称???
	 * </pre>
	 * 
	 * @return
	 */
	@Bean
	@Scope("prototype")
	public Feign.Builder feignBuilder() {
		return Feign.builder();
	}

}
