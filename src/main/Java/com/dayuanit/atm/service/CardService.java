package com.dayuanit.atm.service;

import java.util.List;

import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.domain.Transfer;
import com.dayuanit.atm.dto.BankCardDto;
import com.dayuanit.atm.dto.DetailDTO;
import com.dayuanit.atm.util.PageUtils;

public interface CardService {
	
	void openAccount(int userId, int amount);
	
	PageUtils<BankCardDto> listCard(int userId, int currentPageNum);

	Card getCard(String cardNum, int cardId);

	PageUtils<DetailDTO> listDetail(int userId, int cardId, int currentPageNum);

	void deposit(int cardId, int amount, int userId);

	void asynTransfer(int amount, int outCardId, String inCardNum, int userId);

	void transfer4In(Transfer transfer);

	void draw(int cardId, int amount, int userId);

	void deleteCard(int cardId, int userId);
}
