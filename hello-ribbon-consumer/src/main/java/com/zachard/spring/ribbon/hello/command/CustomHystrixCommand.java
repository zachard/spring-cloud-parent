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

package com.zachard.spring.ribbon.hello.command;

import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.HystrixCommand;

/**
 * 自定义<code>Hystrix</code>命令
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
public class CustomHystrixCommand extends HystrixCommand<String> {
	
	private RestTemplate restTemplate;

	public CustomHystrixCommand(Setter setter, RestTemplate restTemplate) {
		super(setter);
		this.restTemplate = restTemplate;
	}

	@Override
	protected String run() throws Exception {
		return restTemplate.getForObject("http://zachard-service-1/discovery", String.class);
	}

}
