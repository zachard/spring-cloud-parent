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

package com.zachard.spring.cloud.hello.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zachard.spring.cloud.hello.dao.UserDao;
import com.zachard.spring.cloud.hello.model.User;
import com.zachard.spring.cloud.hello.service.UserService;

/**
 * <code>User</code>模块Service层实现类
 * <pre>
 * </pre>
 *
 * @author zachard
 * @version 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 根据用户编号查询用户信息
	 * 
	 * @param id    用户编号
	 * @return      用户信息
	 */
	@Override
	public User queryById(Long id) {
		return userDao.queryById(id);
	}
	
	/**
	 * 根据用户编号列表查询用户列表
	 * 
	 * @param ids    用户编号列表
	 * @return       用户信息列表
	 */
	@Override
	public List<User> queryListByIds(String ids) {
		return userDao.queryListByIds(Arrays.asList(ids.split(","))
				.stream().map(Long::valueOf)
				.collect(Collectors.toList()));
	}

}
