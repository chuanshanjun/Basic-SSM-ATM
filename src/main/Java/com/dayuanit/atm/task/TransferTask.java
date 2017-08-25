package com.dayuanit.atm.task;

import com.dayuanit.atm.Exception.ATMException;
import com.dayuanit.atm.controller.CardController;
import com.dayuanit.atm.domain.Transfer;
import com.dayuanit.atm.mapper.CardMapper;
import com.dayuanit.atm.mapper.TransferMapper;
import com.dayuanit.atm.service.CardService;
import com.dayuanit.atm.serviceImpl.CardServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YOUNG on 2017/8/24.
 */
@Component("transferTask")
public class TransferTask {

    @Autowired
    private TransferMapper transferMapper;

    @Autowired
    private CardService cardService;

    Logger log = LoggerFactory.getLogger(TransferTask.class);

    public void doTask() {
        List<Transfer> list = list = transferMapper.listTransfer4In();
        for (Transfer transfer : list) {
            try {
                log.info("----------" + "正在转账---卡号{}，金额{}", transfer.getInCardNum(), transfer.getAmount());
                cardService.transfer4In(transfer);
            } catch (Exception e) {
                log.error("异步转账失败{}", e.getMessage() , e);
                continue;
            }
        }
    }
}
