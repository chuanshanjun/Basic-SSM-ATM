package com.dayuanit.atm.mapper;

import org.apache.ibatis.annotations.Param;

import com.dayuanit.atm.domain.User;

public interface UserMapper {

	int addUser(User user);
	
	User getUserByUserName(@Param("username")String username);
}
