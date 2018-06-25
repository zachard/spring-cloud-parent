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

package com.zachard.spring.cloud.zuul.hello.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态加载路由配置类
 * 
 * <pre>
 *     (1) {@link ConfigurationProperties}注解用于注解外部配置, 如果想要绑定或是校验一些外部属性, 可将
 *         此注解用于{@link Configuration}注解的类或是{@link Bean}注解的方法之上
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@ConfigurationProperties("zuul.filter")
public class DynamicFilterConfiguration {
	
	/**
	 * 动态加载过滤器的存储路径
	 */
	private String root;
	
	/**
	 * 动态加载过滤器的时间间隔
	 */
	private Integer interval;

	/**
	 * @return the root
	 */
	public String getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(String root) {
		this.root = root;
	}

	/**
	 * @return the interval
	 */
	public Integer getInterval() {
		return interval;
	}

	/**
	 * @param interval the interval to set
	 */
	public void setInterval(Integer interval) {
		this.interval = interval;
	}

}
