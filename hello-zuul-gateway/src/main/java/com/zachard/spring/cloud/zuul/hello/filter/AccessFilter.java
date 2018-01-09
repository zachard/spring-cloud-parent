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

package com.zachard.spring.cloud.zuul.hello.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 权限检查拦截器--主要通过验证HttpServletRequest中是否含有accessToken参数示例
 * 
 * <pre>
 *     (1) {@link ZuulFilter}表示<code>Zuul</code>组件对应的拦截器
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
public class AccessFilter extends ZuulFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);
	
	/**
	 * HTTP请求中对应的Token参数
	 */
	private static final String ACCESS_TOKEN = "accessToken";

	/**
	 * 表示<code>ZuulFilter</code>的具体处理逻辑
	 * 
	 * @return  过滤器处理结果
	 */
	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		logger.info("请求url为: {}, 请求方法为: {}", request.getRequestURL(), request.getMethod());
		
		Object accessToken = request.getParameter(ACCESS_TOKEN);
		
		if (accessToken == null) {
			logger.error("请求token为空, 权限验证失败");
			
			// 设置Zuul路由响应及响应码
//			context.setSendZuulResponse(false);
//			context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
			
			// 抛出异常, 并进入error过滤器, 并不会进入SendErrorFilter过滤器
//			try {
//				throw new RuntimeException("权限token为空");
//			} catch (Exception e) {
//				context.setThrowable(e);
//				context.set("sendErrorFilter.ran", true);
//				context.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//				context.set("error.exception", e);
//			}
			
			 // 抛出异常, 进入自定义ErrorFilter, 必须将自定义拦截器注解为Bean对象
			 throw new RuntimeException("权限token为空");
		}
		
		logger.info("权限认证通过, 请求token为: {}", accessToken);
		return accessToken;
	}

	/**
	 * 表示是否应该拦截
	 * 
	 * @return <code>true</code>表示需要执行拦截器, <code>false</code>表示不需要执行拦截器
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * 定义过滤器执行的顺序, 数值越小优先级越高
	 * 
	 * @return 过滤器执行顺序
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * 表示过滤器的类型--过滤器在哪个生命周期内执行
	 * 
	 * @return  <code>pre</code>表示请求执行之前进行过滤, 
	 *          <code>route</code>表示请求执行时执行过滤器
	 *          <code>post</code>表示请求处理完成之后执行过滤器, 
	 *          <code>error</code>表示请求出现错误时执行过滤器
	 */
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

}
