package com.dayuanit.atm.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.dayuanit.atm.domain.Detail;
import com.dayuanit.atm.domain.Transfer;
import com.dayuanit.atm.dto.BankCardDto;
import com.dayuanit.atm.dto.DetailDTO;
import com.dayuanit.atm.enums.BankStatus;
import com.dayuanit.atm.enums.TransferEnum;
import com.dayuanit.atm.enums.optType;
import com.dayuanit.atm.mapper.DetailMapper;
import com.dayuanit.atm.mapper.TransferMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuanit.atm.Exception.ATMException;
import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.mapper.CardMapper;
import com.dayuanit.atm.service.CardService;
import com.dayuanit.atm.util.PageUtils;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CardServiceImpl implements CardService{

	@Autowired
	private CardMapper cardMapper;

	@Autowired
	private DetailMapper detailMapper;

	@Autowired
	private TransferMapper transferMapper;
	
	private Card card;
	
	private String createBankNum() {
		Random random = new Random();
		String cardNum = "";
		for (int i = 0; i < 4; i++) {
			int num = random.nextInt(10);
			cardNum += String.valueOf(num);
		}
		
		return cardNum;
	}
		
	@Override
	public void openAccount(int userId, int amount) {
		String cardNum = createBankNum();
		
		card = new Card();
		card.setCardNum(cardNum);
		card.setUserId(userId);
		card.setBalance(amount);
		
		int rows = cardMapper.addCard(card);
		
		if (1 != rows) {
			throw new ATMException("开户失败");
		}
	}
	
	@Override
	public PageUtils<BankCardDto> listCard(int userId, int currentPageNum) {
		int total = cardMapper.countCard(userId);
		
		PageUtils<BankCardDto> pu = new PageUtils<BankCardDto>(currentPageNum,total);
		
		List<Card> list = cardMapper.listCard(userId, pu.getSetOff(), pu.PRE_PAGE_NUM);

		List<BankCardDto> listDto = new ArrayList<BankCardDto>();

		for (Card bc : list) {
			BankCardDto bcd = new BankCardDto();
			bcd.setId(bc.getId());
			bcd.setCardNum(bc.getCardNum());
			bcd.setBalance(String.valueOf(bc.getBalance()));
			bcd.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bc.getCreateTime()));

			BankStatus bs = BankStatus.getEnum(bc.getStatus());
			bcd.setStatus(bs.getV());

			listDto.add(bcd);

		}

		pu.setData(listDto);

		return pu;
	}

	@Override
	public Card getCard(String cardNum, int cardId) {
		return cardMapper.getCard(cardNum, cardId);
	}

	@Override
	public PageUtils<DetailDTO> listDetail(int userId, int cardId, int currentPageNum) {
		int total = detailMapper.countDetail(cardId);

		PageUtils<DetailDTO> pu = new PageUtils<DetailDTO>(currentPageNum, total);

		List<Detail> listDetail = detailMapper.listDetail(cardId, pu.getSetOff(), pu.PRE_PAGE_NUM);

		List<DetailDTO> listDTO = new ArrayList<DetailDTO>();

		for (Detail detail : listDetail) {
			DetailDTO detailDTO = new DetailDTO();
			detailDTO.setId(detail.getId());
			detailDTO.setCardId(detail.getCardId());
			detailDTO.setAmount(detail.getAmount());
			detailDTO.setFlowDesc(detail.getFlowDesc());
			detailDTO.setOptType(optType.getEnum(detail.getOptType()).getV());
			detailDTO.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(detail.getCreateTime()));
			listDTO.add(detailDTO);
		}

		pu.setData(listDTO);

		return pu;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deposit(int cardId, int amount, int userId) {

		if (amount < 0) {
			throw new ATMException("金额不能小于零");
		}

		Card testCard = cardMapper.getCard(null, cardId);

		if (null == testCard) {
			throw new ATMException("银行卡不存在");
		}

		if (userId != testCard.getUserId().intValue()) {
			throw new ATMException("无权操作");
		}

		int rows = cardMapper.updataBalance(amount, cardId);

		if (1 != rows) {
			throw new ATMException("存款失败");
		}

		Detail detail = new Detail();
		detail.setUserId(userId);
		detail.setCardId(cardId);
		detail.setAmount(amount);
		detail.setOptType(1);
		detail.setFlowDesc("存款");

		rows = detailMapper.addDetail(detail);

		if (1 != rows) {
			throw new ATMException("存款失败");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)//让spring对于Exception进行事务的回滚
	public void asynTransfer(int amount, int outCardId, String inCardNum, int userId) {
		//金额校验
		if (amount <= 0) {
			throw new ATMException("金额正确");
		}

		//入账卡号是否存在
		Card inCard = cardMapper.getCard(inCardNum, null);
		if (null == inCard) {
			throw new ATMException("入账卡号不存在");
		}

		//出账卡号校验，在此时使用悲观锁(for update)，悲观锁配合事务才能生效
		//在这边查询的时候就用悲观锁把其他线程挡住
		Card outCard = cardMapper.getCard4Lock(null, outCardId);
		if (null == outCard) {
			throw new ATMException("出账卡号不存在");
		}

		//校验此卡是否属于UserId名下
		if (outCard.getUserId().intValue() != userId) {
			throw new ATMException(outCard.getCardNum() + "此卡不属于你");
		}

		//检查余额是否足够
		if (amount > outCard.getBalance()) {
			throw new ATMException("余额不够");
		}

		int rows = cardMapper.updataBalance(-amount, outCardId);
		if (1 != rows) {
			throw new ATMException("转账失败");
		}

		Detail detail = new Detail();
		detail.setUserId(userId);
		detail.setCardId(outCardId);
		detail.setAmount(-amount);
		detail.setFlowDesc("转账支出");
		detail.setOptType(optType.TRANSFEROUT.getCode());

		rows = detailMapper.addDetail(detail);

		if (1 != rows) {
			throw new ATMException("转账失败");
		}

		Transfer transfer = new Transfer();
		transfer.setAmount(amount);
		transfer.setOutCardNum(outCard.getCardNum());
		transfer.setOutCardId(outCardId);
		transfer.setInCardNum(inCardNum);
		transfer.setInCardId(inCard.getId());
		transfer.setStatus(TransferEnum.un_transfer.getK());

		rows = transferMapper.add(transfer);

		if (1 != rows) {
			throw  new ATMException("转账失败");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void transfer4In(Transfer transfer) {

		if (transfer.getStatus().intValue() != TransferEnum.un_transfer.getK()) {
			return;//如果此时表格数据已被修改了，那么不需要再往下执行了
		}

		//入账卡号是否存在，并使用悲观锁配合事务
		Card inCard = cardMapper.getCard4Lock(transfer.getInCardNum(), null);

		if (transfer.getStatus().intValue() != TransferEnum.un_transfer.getK()) {
			return;//进入锁之后再次查询下
		}

		if (null == inCard) {
			throw new ATMException("入账卡不存在");
		}

		int rows = cardMapper.updataBalance(transfer.getAmount(), inCard.getId());

		if (1 != rows) {
			throw new ATMException("转账失败");
		}

		Detail detail = new Detail();
		detail.setAmount(transfer.getAmount());
		detail.setFlowDesc("转账收入");
		detail.setCardId(inCard.getId());
		detail.setUserId(inCard.getUserId());
		detail.setOptType(optType.TRANSFERIN.getCode());

		rows = detailMapper.addDetail(detail);

		if (1 != rows) {
			throw new ATMException("转账失败");
		}

		transfer.setStatus(TransferEnum.already_transfer.getK());
		rows = transferMapper.updateStatus(transfer);

		if (1 != rows) {
			throw new ATMException("转账失败");
		}
	}
}
