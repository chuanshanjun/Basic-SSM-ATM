package com.dayuanit.atm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dayuanit.atm.domain.Card;

public interface CardMapper {
	
	int addCard(Card card);
	
	Card getCard(@Param("cardNum")String cardNum, @Param("cardId")Integer cardId);
	
	int countCard(@Param("userId")Integer userId);
	
	List<Card> listCard(@Param("userId")Integer userId, 
			@Param("offSet")Integer offSet, 
			@Param("prePageNum")Integer prePageNum);

	int updataBalance(@Param("amount") Integer amount, @Param("cardId") Integer cardId);

	Card getCard4Lock(@Param("cardNum")String cardNum, @Param("cardId")Integer cardId);

	int updataStatus(@Param("cardId")Integer cardId);
}
