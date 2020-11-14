package com.service.impl;

import java.net.InterfaceAddress;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.kisso.common.encrypt.SaltEncoder;
import com.mapper.UserMapper;
import com.model.User;
import com.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	//@Resource
	/*@Autowired
	private UserMapper userMapper;*/

	@Resource
	private UserMapper userMapper;

	@Override
	public int addUser(User user) {
		int userid = userMapper.addUser(user);
		return userid;
	}

	@Override
	public boolean checkUserByUsername(String username) {
		if(userMapper.checkUserByUsername(username) == 1){
			return false;
		}
		return  true;
	}
	
	@Override
	public long validUserAndPassword(User user) {
		List<User> users = userMapper.getUserInfoByName(user);
		if (users.isEmpty()) {
			return -1;// 不存在
		}
		User info = users.get(0);
		System.out.println("获取的user信息为:"+info);
		if (SaltEncoder.md5SaltValid(user.getUsername(), info.getPassword(), user.getPassword())) {
			System.out.println("返回的信息"+info.getId());
			return info.getId();
		} else {
			return -1;// 不存在
		}
	}

	@Override
	public List<User> findAllUser() {
		return userMapper.findAllUser();
	}
}
