package com.dayuanit.atm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dayuanit.atm.Exception.ATMException;
import com.dayuanit.atm.domain.User;

public abstract class BaseController {
	
	protected static final String LOGIN_FLAG = "login_user_flag";
	
	protected int getUserId(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		
		if (null == session) {
			throw new ATMException("用户未登录");
		}
		
		Object object = session.getAttribute(LOGIN_FLAG);
		
		if (null == object) {
			throw new ATMException("用户未登录");
		}
		
		User user = (User)object;
		
		return user.getId();
	}
	
	protected String getUserName(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		
		if (null == session) {
			throw new ATMException("客户未登录");
		}
		
		Object object = session.getAttribute(LOGIN_FLAG);
		
		if (null == object) {
			throw new ATMException("用户未登录"); 
			}
		User user = (User)object;
		
		return user.getUsername();
	}
}
