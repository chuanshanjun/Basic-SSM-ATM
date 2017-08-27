package com.dayuanit.atm.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dayuanit.atm.task.TransferTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dayuanit.atm.Exception.ATMException;
import com.dayuanit.atm.domain.User;
import com.dayuanit.atm.dto.AjaxResultDTO;
import com.dayuanit.atm.service.UserService;
import com.dayuanit.atm.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;


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
		log.info("打印注册信息:{}", userVO.getUsername());
		try {
			String code = (String)session.getAttribute("code");
			log.info("正在注册 用户名：{}", userVO.getUsername());
			if (!userVO.getCode().equals(code)) {
				session.removeAttribute("code");
				return AjaxResultDTO.failed("验证码错误");
			}

			userService.regist(userVO.getUsername(), userVO.getPasswordOther(), userVO.getPasswordOther());
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

	@RequestMapping("/readFile")
	public void readFile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		BufferedInputStream bis = null;
		OutputStream os = null;

		try {
			os = resp.getOutputStream();
			String rootPath = req.getServletContext().getRealPath("/");
			String uploadPath = rootPath + "/WEB-INF/photo";
			String fileName = "photo-" + getUserId(req) + ".jpg";
			String filePath = uploadPath + "/" + fileName;
			File file = new File(filePath);
			if (!file.exists()) {
				log.info("图片没找到");
			}

			bis = new BufferedInputStream(new FileInputStream(file));

			byte[] buff = new byte[1024];
			int length = -1;

			while (-1 != (length = bis.read(buff))) {
				os.write(buff, 0, length);
				os.flush();
			}
		} catch (Exception e) {
			resp.sendError(500);
		} finally {
			if (null != bis) {
				bis.close();
			}

			if (null != os) {
				os.close();
			}
		}
	}

	@RequestMapping("/toUpload")
	public String toUpload() {
		return "upload";
	}

	@RequestMapping("/upload")
	public String upload(@RequestParam("desc") String desc, @RequestParam("fileOne") MultipartFile file, HttpServletRequest req, HttpServletResponse resp) {
		log.info("desc={}-----------------------", desc);
		log.info("fileOne={}--------------------", file);

		System.out.println("desc===========" + desc);
		System.out.println("file===========" + file);

		String root = req.getServletContext().getRealPath("/");
		String uploadPath = root + "/WEB-INF/photo";

		String fileName = "photo-" + getUserId(req) + ".jpg";
		String filePath = uploadPath + "/" + fileName;

		File targetFile = new File(filePath);

		try {
			file.transferTo(targetFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/";
	}
}
