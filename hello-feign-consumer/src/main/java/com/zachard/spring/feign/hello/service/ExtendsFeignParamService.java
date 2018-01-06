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

package com.zachard.spring.feign.hello.service;

import org.springframework.cloud.netflix.feign.FeignClient;

import com.zachard.spring.feign.hello.configuration.DisableHystrixConfiguration;

/**
 * 通过继承特性实现<code>Feign</code>方式服务调用--服务消费方Service
 * 
 * <pre>
 *     (1) {@link FeignClient}注解中的<code>configuration</code>属性指定了构建<b>Feign</b>客户端的配置类
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@FeignClient(name = "zachard-service-1", configuration = DisableHystrixConfiguration.class)
public interface ExtendsFeignParamService extends com.zachard.service.api.hello.service.ExtendsFeignParamService {

}
