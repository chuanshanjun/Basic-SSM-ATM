package com.dayuanit.atm.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.vo.TransferVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.dayuanit.atm.Exception.ATMException;
import com.dayuanit.atm.dto.AjaxResultDTO;
import com.dayuanit.atm.mapper.CardMapper;
import com.dayuanit.atm.service.CardService;
import com.dayuanit.atm.util.PageUtils;

@Controller
@RequestMapping("/card")
public class CardController extends BaseController{
	
	@Autowired
	private CardService cardService;
	
	@RequestMapping("/openAccount")
	@ResponseBody
	public AjaxResultDTO openAccount(HttpServletRequest req, @RequestParam(required=false, defaultValue="0")int amount) {
		try {
			cardService.openAccount(getUserId(req), amount);
		} catch (ATMException ae) {
			return AjaxResultDTO.failed(ae.getMessage());
		}
		
		return AjaxResultDTO.success();
	}
	
	@RequestMapping("/query")
	@ResponseBody
	public AjaxResultDTO query(HttpServletRequest req, String pageNum) {
		
		PageUtils pageUtils = null;
		try {
			pageUtils = cardService.listCard(getUserId(req), Integer.parseInt(pageNum));
			
		} catch (ATMException ae) {
			AjaxResultDTO.failed(ae.getMessage());
		}
		
		return AjaxResultDTO.success(pageUtils);
	}

	@RequestMapping("/toCardDetail")
	public String toCardDetail(HttpServletRequest req, @ModelAttribute("cardId") String cardId,
							   ModelMap mm, @CookieValue("JSESSIONID")String jessionId) {
		//req.setAttribute("cardId", cardId); 有了ModelAttribute就不需要set直接在页面上用EL取值即可
		//mm.addAttribute("cardId", cardId);
		//req.setAttribute("card", card);
		//String cardId = req.getParameter("cardID");
		Card card = cardService.getCard(null, Integer.parseInt(cardId));
		mm.addAttribute("card", card);

		System.out.println("jessionId=" + jessionId);

		Cookie[] cookieArray = req.getCookies();
		for (Cookie ck : cookieArray) {
			System.out.println("cookiedomain=" + ck.getDomain());
			System.out.println("cookiepath=" + ck.getPath());
			System.out.println("cookiemaxage=" + ck.getMaxAge());
			System.out.println("cookiename=" + ck.getName() + "cookievalue=" + ck.getValue());
		}

		return "cardDetail";
	}

	@RequestMapping("/listDetail")
	@ResponseBody
	public AjaxResultDTO listDetail(HttpServletRequest req, int cardId,
									@RequestParam(required = false, defaultValue = "1")int pageNum) {
		System.out.println("-----------");
		PageUtils pu = null;

		try {
			pu = cardService.listDetail(getUserId(req), cardId, pageNum);
		} catch (ATMException ae) {
			AjaxResultDTO.failed(ae.getMessage());
		}

		return AjaxResultDTO.success(pu);
	}

	@RequestMapping("/toDeposit")
	public String toDeposit(int cardId, ModelMap mm) {
		Card card = cardService.getCard(null, cardId);

		mm.addAttribute("card", card);

		return "deposit";
	}

	@RequestMapping("/deposit")
	@ResponseBody
	public AjaxResultDTO deposit(int cardId, int amount, HttpServletRequest req) {
		try {
			cardService.deposit(cardId, amount, getUserId(req));
		} catch (ATMException ae) {
			AjaxResultDTO.failed(ae.getMessage());
		}

		return AjaxResultDTO.success();
	}

	@RequestMapping("/toTransfer")
	public String toTransfer(int cardId, ModelMap mm) {
		Card card = cardService.getCard(null, cardId);
		mm.addAttribute("card", card);

		return "transfer";
	}

	@RequestMapping("/transfer")
	@ResponseBody
	public AjaxResultDTO transfer(TransferVO transferVO, HttpServletRequest req) {
		try {
			cardService.asynTransfer(transferVO.getAmount(),
					transferVO.getCardId(), transferVO.getInCardNum(), getUserId(req));
		} catch (ATMException ae) {
			return AjaxResultDTO.failed(ae.getMessage());
		} catch (Exception e) {
			return AjaxResultDTO.failed("转账失败");
		}

		return AjaxResultDTO.success();
	}
}
