package com.dayuanit.atm.service;

import com.dayuanit.atm.domain.User;

public interface UserService {
	
	void regist(String username, String password, String otherPassword);
	
	User login(String username, String password);
}
