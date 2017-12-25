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

package com.zachard.spring.cloud.hello.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.zachard.spring.cloud.hello.dao.UserDao;
import com.zachard.spring.cloud.hello.model.User;

/**
 * <code>User</code>模块DAO接口实现类
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@Service
public class UserDaoImpl implements UserDao {
	
	/**
	 * 模拟数据库的User表
	 */
	private static List<User> users = new ArrayList<>();
	
	// 对Users列表进行初始化, 模拟数据库保存的用户列表
	static {
		users.add(new User(1L, "Amy"));
		users.add(new User(2L, "Bob"));
		users.add(new User(3L, "Cary"));
		users.add(new User(4L, "David"));
		users.add(new User(5L, "Eric"));
		users.add(new User(6L, "Frank"));
		users.add(new User(7L, "Irya"));
		users.add(new User(8L, "Jack"));
		users.add(new User(9L, "Karry"));
	}
	
	/**
	 * 根据用户编号查询用户信息
	 * 
	 * @param id    用户编号
	 * @return      用户信息
	 */
	@Override
	public User queryById(Long id) {
		return users.stream()
				.filter(user -> Objects.equals(user.getId(), id))
				.findFirst().get();
	}
	
	/**
	 * 根据用户编号列表查询用户列表
	 * 
	 * @param ids    用户编号列表
	 * @return       用户信息列表
	 */
	@Override
	public List<User> queryListByIds(List<Long> ids) {
		return users.stream()
				.filter(user -> ids.contains(user.getId()))
				.collect(Collectors.toList());
	}

}
