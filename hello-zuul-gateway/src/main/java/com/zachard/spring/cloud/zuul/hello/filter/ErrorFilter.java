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

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * <code>Zuul</code>自定义的错误拦截器
 * 
 * <pre>
 *     (1) 注意: 必须将{@link ErrorFilter}注解为<code>Bean</code>对象
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@Component
public class ErrorFilter extends ZuulFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(ErrorFilter.class);

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
	 * 表示<code>ZuulFilter</code>的具体处理逻辑
	 * 
	 * @return  过滤器处理结果
	 */
	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		Throwable throwable = context.getThrowable();
		logger.error("发生错误时的拦截器, 引起错误的原因为: {}", throwable.getCause().getMessage());
		context.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		context.set("error.exception", throwable.getCause());
		
		return null;
	}

	/**
	 * 定义过滤器执行的顺序, 数值越小优先级越高
	 * 
	 * @return 过滤器执行顺序
	 */
	@Override
	public int filterOrder() {
		return 10;
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
		return FilterConstants.ERROR_TYPE;
	}

}
