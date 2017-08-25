package com.dayuanit.atm.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.mapper.CardMapper;
import com.dayuanit.atm.mapper.UserMapper;

@ContextConfiguration("/spring/spring-app.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CardMapperTest {
	
	private Card card;
	
	@Autowired
	private CardMapper cardMapper;
	
	@Before
	public void init() {
		card = new Card();
		card.setUserId(3);
		card.setCardNum("555555");
	}
	
	@Test
	@Rollback
	public void testAddCard() {
		int rows = cardMapper.addCard(card);
		assertEquals(1, rows);
	}
	
	@Test
	@Rollback
	public void testCountCard() {
		cardMapper.addCard(card);
		int rows = cardMapper.countCard(card.getUserId());
		assertEquals(1, rows);
	}
	
	@Test
	@Rollback
	public void testGetCard() {
		cardMapper.addCard(card);
//		Card testCard = cardMapper.getCard(card.getCardNum(), null);
//		int rows = testCard.getUserId();
//		System.out.println(rows);
//		assertEquals(3, rows);
		Card testCard = cardMapper.getCard(null, card.getId());
		int rows = testCard.getUserId();
		assertEquals(3, rows);
	}
	
	@Test
	@Rollback
	public void testListCard() {
		List<Card> listCard = cardMapper.listCard(4, 0, 2);
		assertEquals(2, listCard.size());
	}

	@Test
	@Rollback
	public void testUpdataBalance() {
		int rows = cardMapper.updataBalance(100, 9);
		assertEquals(1, rows);
	}
}
