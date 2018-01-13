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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;
import com.zachard.spring.cloud.zuul.hello.configuration.DynamicFilterConfiguration;
import com.zachard.spring.cloud.zuul.hello.filter.AccessFilter;

/**
 * <code>Zuul</code>网关服务启动入口类
 * 
 * <pre>
 *     (1) {@link SpringCloudApplication}注解作用等同于程序同时加入{@link SpringBootApplication}注解
 *         {@link EnableDiscoveryClient}服务发现注解及{@link EnableCircuitBreaker}断路器注解
 *     (2) {@link EnableZuulProxy}注解用于构建一个<code>Zuul</code>服务端并为其安装一些反向代理过滤器, 
 *         这样<code>Zuul</code>服务端可以实现将请求转发到后端服务器. 后端服务的注册可以通过手动配置方式或是
 *         <code>DiscoveryClient</code>注册中心方式实现
 *     (3) {@link EnableConfigurationProperties}注解表示开启配置属性功能
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@EnableZuulProxy
@EnableConfigurationProperties({DynamicFilterConfiguration.class})
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
	
	/**
	 * 将一个{@link AccessFilter}过滤器对象注解为<code>Bean</code>对象
	 * 
	 * @return    <code>AccessFilter</code>过滤器对象
	 */
	@Bean
	public AccessFilter getAccessFilter() {
		return new AccessFilter();
	}
	
	/**
	 * 将Zuul自定义的错误拦截器注入为Bean
	 * 
	 * @return    <code>SendErrorFilter</code>过滤器对象
	 */
	@Bean
	public SendErrorFilter getSendErrorFilter() {
		return new SendErrorFilter();
	}
	
	/**
	 * 自定义路由映射器
	 * 
	 * @return    路由映射器对象
	 */
	@Bean
	public PatternServiceRouteMapper getPatternServiceRouteMapper() {
		return new PatternServiceRouteMapper(
				"(?<name>^.+)-(?<version>v.+$)",
				"${version}/${name}");  
	}
	
	/**
	 * 创建一个{@link ZuulProperties}属性对象
	 * 
	 * <pre>
	 *     (1) {@link RefreshScope}注解表示开启refresh机制, 当在配置中心客户端请求<code>/refresh</code>
	 *         的时候, 会重新从配置中心的服务端获取值
	 *     (2) {@link ConfigurationProperties}注解用于注解外部配置, 如果想要绑定或是校验一些外部属性, 可将
	 *         此注解用于{@link Configuration}注解的类或是{@link Bean}注解的方法之上
	 * </pre>
	 * 
	 * @return
	 */
	@Bean
	@RefreshScope
	@ConfigurationProperties("zuul")
	public ZuulProperties zuulProperties() {
		return new ZuulProperties();
	}
	
	/**
	 * 创建一个过滤器加载器对象
	 * 
	 * <pre>
	 *     (1) {@link FilterLoader}用于加载编译过滤器类文件并检查它的源代码是否被改变
	 * </pre>
	 * 
	 * @param dynamicFilterConfiguration
	 * @return
	 */
	@Bean
	public FilterLoader filterLoader(DynamicFilterConfiguration dynamicFilterConfiguration) {
		FilterLoader filterLoader = FilterLoader.getInstance();
		// GroovyCompiler编译用于编译FilterLoader加载的文件
		filterLoader.setCompiler(new GroovyCompiler());
		
		try {
			/*
			 * FilterFileManager用于轮询指定目录下文件改变, 轮询的路径及时间间隔在初始化类时指定, 
			 * 之后轮询器将监测指定目录下的修改
			 */
			FilterFileManager.setFilenameFilter(new GroovyFileFilter());
			FilterFileManager.init(
					dynamicFilterConfiguration.getInterval(), 
					dynamicFilterConfiguration.getRoot() + "/" + FilterConstants.PRE_TYPE, 
					dynamicFilterConfiguration.getRoot() + "/" + FilterConstants.POST_TYPE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return filterLoader;
	}

}
