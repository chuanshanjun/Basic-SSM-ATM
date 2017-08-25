package com.dayuanit.atm.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dayuanit.atm.task.TransferTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dayuanit.atm.Exception.ATMException;
import com.dayuanit.atm.domain.User;
import com.dayuanit.atm.dto.AjaxResultDTO;
import com.dayuanit.atm.service.UserService;
import com.dayuanit.atm.vo.UserVO;


@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

	Logger log = LoggerFactory.getLogger(UserController.class);

	public UserController() {
		System.out.println("-------UserController con-------");
	}
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/toRegist")
	public String toRegist() {
		return "regist";
	}
	
	@RequestMapping("/regist")
	@ResponseBody
	public AjaxResultDTO regist(UserVO userVO, HttpSession session) {
		try {

			String code = (String)session.getAttribute("code");
			log.info("正在注册 用户名:{}", userVO.getUsername());
			if (!userVO.getCode().equals(code)) {
				session.removeAttribute("code");
				return AjaxResultDTO.failed("验证码错误");
			}

			userService.regist(userVO.getUsername(), userVO.getPassword(), userVO.getPasswordOther());
		} catch (ATMException ae) {
			return AjaxResultDTO.failed(ae.getMessage());
		}
		
		return AjaxResultDTO.success();
	}
	
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("login")
	@ResponseBody
	public AjaxResultDTO login(String username, String password, HttpServletRequest req) {
		try {
			User user = userService.login(username, password);
			req.getSession().setAttribute(LOGIN_FLAG, user);
			log.info("正在登陆用户名{}，密码{}", username, password);
		} catch (ATMException ae) {
			return AjaxResultDTO.failed(ae.getMessage());
		} 
		
		return AjaxResultDTO.success();
	}
	
	@RequestMapping("/loginOut")
	public String loginOut(HttpServletRequest req) {
		req.getSession().invalidate();
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/toUserCenter")
	public String toUserCenter() {
		return "userCenter";
	}

//	public static void main(String[] args) {
//		Logger log = LoggerFactory.getLogger(UserController.class);
//		log.info("i am in fo mmmm");
//	}
}
