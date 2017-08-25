package com.dayuanit.atm.serviceImpl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuanit.atm.Exception.ATMException;
import com.dayuanit.atm.domain.User;
import com.dayuanit.atm.mapper.UserMapper;
import com.dayuanit.atm.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	public UserServiceImpl() {
		System.out.println("========UserServiceImpl con========");
	}
	
	private User user;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void regist(String username, String password, String otherPassword) {
		if ("".equals(username) || null == username) {
			throw new ATMException("用户名不能为空");
		}
		
		if ("".equals(password) || null == password) {
			throw new ATMException("用户名不能为空");
		}
		
		if ("".equals(otherPassword) || null == otherPassword) {
			throw new ATMException("用户名不能为空");
		}
		
		if (!password.equals(otherPassword)) {
			throw new ATMException("密码不一致");
		}

		password += username;
		password = DigestUtils.md5Hex(password);;

		user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		int rows = userMapper.addUser(user);
		
		if (1 != rows) {
			throw new ATMException("注册失败");
		}
	}
	
	@Override
	public User login(String username, String password) {
		
		if ("".equals(username) || null == username) {
			throw new ATMException("用户名不能为空");
		}
		
		if ("".equals(password) || null == password) {
			throw new ATMException("密码不能为空");
		}
		
		User testUser = userMapper.getUserByUserName(username);
		
		if (null == testUser) {
			throw new ATMException("用户名或密码不正确");
		}

		password += username;
		password = DigestUtils.md5Hex(password);

		if (!password.equals(testUser.getPassword())) {
			throw new ATMException("用户名或密码不正确");
		}
		
		return testUser;
	}

}
