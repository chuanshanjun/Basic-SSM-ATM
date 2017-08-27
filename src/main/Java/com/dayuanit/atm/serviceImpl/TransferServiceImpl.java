package com.dayuanit.atm.serviceImpl;

import com.dayuanit.atm.Exception.ATMException;
import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.domain.Transfer;
import com.dayuanit.atm.enums.TransferEnum;
import com.dayuanit.atm.mapper.CardMapper;
import com.dayuanit.atm.mapper.TransferMapper;
import com.dayuanit.atm.service.CardService;
import com.dayuanit.atm.service.TransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferServiceImpl implements TransferService{

    private Logger log = LoggerFactory.getLogger(TransferServiceImpl.class);

    @Autowired
    private CardService cardService;

    @Autowired
    private CardMapper cardMapper;

    @Autowired
    private TransferMapper transferMapper;


    @Override//propagation = Propagation.REQUIRES_NEW新建事务，如果当前存在事务，把当前事务挂起。
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void backMoney(Transfer transfer) {

        //使用悲观锁
        cardMapper.getCard4Lock(transfer.getInCardNum(), null);

        //为了防止多线程的情况，当上好锁之后在查询一次，保证数据最新的
        transfer = transferMapper.getTransferById(transfer.getId());

        log.info("在transfer上部的状态:{}", transfer.getStatus());

        //如果transfer的状态不为待转账，那么说明此比交易已经成功，或者已经取消，所以直接返回
        if (transfer.getStatus().intValue() != TransferEnum.un_transfer.getK()) {
            return;
        }

        //获取出账卡
        Card card = cardMapper.getCard(null, transfer.getOutCardId());

        //将钱存回出账卡
        cardService.deposit(card.getId(), transfer.getAmount(), card.getUserId());

        //将transfer的状态设置为取消
        transfer.setStatus(TransferEnum.cancle.getK());

        //将transfer的状态更新至数据库
        int rows = transferMapper.updateStatus(transfer);

        log.info("打印transfer在transferServiceImpl中的的status的最新状态:{}" , transfer.getStatus());

        if (1 != rows) {
            throw new ATMException("转账异步异常");
        }

    }
}
