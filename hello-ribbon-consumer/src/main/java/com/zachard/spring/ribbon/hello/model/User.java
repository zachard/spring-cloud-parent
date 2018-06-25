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

package com.zachard.spring.ribbon.hello.model;

import java.io.Serializable;

/**
 * 用户模块对应实体类
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
public class User implements Serializable {

	/**
	 * 序列化ID，显示声明时有如下好处:
	 * <pre>
	 *   1. 提高程序运行效率
	 *   2. 避免不同操作系统间计算方式不一致而导致生成值不一致
	 *   3. 避免类文件修改后导致同一个类出现不同ID值情况
	 * </pre>
	 */
	private static final long serialVersionUID = -6700624115910182616L;
	
	private Long id;
	private String name;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
