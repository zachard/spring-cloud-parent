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

package com.zachard.spring.cloud.hello.dao;

import java.util.List;

import com.zachard.spring.cloud.hello.model.User;

/**
 * <code>User</code>模块持久化层接口
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
public interface UserDao {
	
	/**
	 * 根据用户编号查询用户信息
	 * 
	 * @param id    用户编号
	 * @return      用户信息
	 */
	User queryById(Long id);
	
	/**
	 * 根据用户编号列表查询用户列表
	 * 
	 * @param ids    用户编号列表
	 * @return       用户信息列表
	 */
	List<User> queryListByIds(List<Long> ids);

}
